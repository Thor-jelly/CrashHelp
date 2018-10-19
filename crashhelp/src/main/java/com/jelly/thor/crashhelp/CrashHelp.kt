package com.jelly.thor.crashhelp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*


/**
 * 类描述：实现UncaughtExceptionHandler来实现获取应用全局的crash信息 <br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2018/7/3 13:41 <br/>
 */
@SuppressLint("StaticFieldLeak")
object CrashHelp : Thread.UncaughtExceptionHandler {
    private lateinit var mBuilder: Builder

    private lateinit var mDefaultCaughtExceptionHandler: Thread.UncaughtExceptionHandler

    private lateinit var mContext: Context

    fun builder(context: Context): Builder {
        mContext = context
        mBuilder = Builder()
        //获取默认的系统异常捕获器
        mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mBuilder
    }

    override fun uncaughtException(t: Thread?, e: Throwable) {
        val initialized = ::mBuilder.isInitialized
        if (!initialized) {
            return
        }

        val parseCrash = parseCrash(e)
        if (mBuilder.mOnCrashListener != null) {
            mBuilder.mOnCrashListener!!.onCrash(t, e, parseCrash)
        }

        if (mBuilder.mEnable) {
            //拦截系统crash弹窗
            if (mBuilder.mShowCrashMessage) {
                val intent = Intent(mContext, CrashActivity::class.java)
                intent.putExtra(CrashActivity.CRASH_BEAN, parseCrash)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(intent)
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            mDefaultCaughtExceptionHandler.uncaughtException(t, e);
        }
    }

    /**
     * 生成crash信息bean
     */
    private fun parseCrash(e: Throwable): CrashBean {
        //包名-暂时不用
        //主体错误信息
        val exceptionMsg = e.message

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        pw.flush()
        pw.close()
        val stackTrace = e.stackTrace
        lateinit var exceptionClassName: String
        lateinit var exceptionFileName: String
        lateinit var exceptionMethod: String
        var lineNum = 0
        if (stackTrace.isNotEmpty()) {
            val stackTraceElement = stackTrace[0]
            //崩溃类名
            exceptionClassName = stackTraceElement.className
            //崩溃文件名
            exceptionFileName = stackTraceElement.fileName
            //崩溃方法
            exceptionMethod = stackTraceElement.methodName
            //崩溃行数
            lineNum = stackTraceElement.lineNumber
        }
        //崩溃类型
        val exceptionType = e.javaClass.name
        //全部崩溃信息
        val allExceptionMsg = sw.toString()
        //崩溃时间
        val time = Date().time
        //设备信息-默认值

        return CrashBean(
                e,
                mContext.packageName,
                exceptionMsg!!,
                exceptionClassName,
                exceptionFileName,
                exceptionMethod,
                lineNum,
                exceptionType,
                allExceptionMsg,
                time
        )
    }


    class Builder {
        /**
         * 设置是否捕获异常，不弹出崩溃框
         */
        internal var mEnable: Boolean = false
        /**
         * 是否显示crash信息
         */
        internal var mShowCrashMessage: Boolean = false
        /**
         * 是否有crash监听
         */
        internal var mOnCrashListener: OnCrashListener? = null

        /**
         * 设置是否拦截系统崩溃弹窗
         */
        public fun setEnable(enable: Boolean): Builder {
            this.mEnable = enable
            return this
        }

        /**
         * 是否显示crash信息
         */
        public fun setShowCrashMessage(showCrashMessage: Boolean): Builder {
            this.mShowCrashMessage = showCrashMessage
            return this
        }

        /**
         * 回调所有异常信息
         */
        public fun setOnCrashListener(onCrashListener: OnCrashListener): Builder {
            this.mOnCrashListener = onCrashListener
            return this
        }
    }
}