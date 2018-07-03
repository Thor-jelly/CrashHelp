package com.jelly.thor.crashhelp
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_crash.*
import java.text.SimpleDateFormat

class CrashActivity : AppCompatActivity() {
    companion object {
        val CRASH_BEAN = "crash_bean"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        val crashBean = intent.getParcelableExtra<CrashBean>(CRASH_BEAN)


        //崩溃主要信息
        mMainMsg.text = crashBean.exceptionMsg
        //包名
        mPackageName.text = crashBean.packageName
        //崩溃类名
        mClassName.text = crashBean.className
        //崩溃文件名
        mFileName.text = crashBean.fileName
        //崩溃方法
        mMethodName.text = crashBean.methodName
        //崩溃行数
        mLineNum.text = crashBean.lineNum.toString()
        //崩溃类型
        mType.text = crashBean.exceptionType
        //崩溃时间
        mTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(crashBean.time)

        //设备信息
        val device = crashBean.device
        mModel.text = device.model
        mBrand.text = device.brand
        mVersion.text = device.version

        //全部崩溃信息
        mAllException.text = crashBean.allExceptionMsg
    }
}
