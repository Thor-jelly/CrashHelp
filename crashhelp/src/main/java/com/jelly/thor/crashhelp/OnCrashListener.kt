package com.jelly.thor.crashhelp


/**
 * 类描述：crash回调<br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2018/7/3 13:56 <br/>
 */
public interface OnCrashListener{
    fun onCrash(t: Thread?, e: Throwable, parseCrash: CrashBean)
}