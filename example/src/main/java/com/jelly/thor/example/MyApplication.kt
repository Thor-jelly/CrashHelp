package com.jelly.thor.example

import android.app.Application
import com.jelly.thor.crashhelp.CrashBean
import com.jelly.thor.crashhelp.CrashHelp
import com.jelly.thor.crashhelp.OnCrashListener

/**
 * 类描述：//TODO:(这里用一句话描述这个方法的作用)    <br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2018/7/3 17:56 <br/>
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        CrashHelp.builder(this)
                .setEnable(true)
                .setShowCrashMessage(true)
                .setOnCrashListener(object : OnCrashListener {
                    override fun onCrash(t: Thread?, e: Throwable, parseCrash: CrashBean) {

                    }
                })
    }
}