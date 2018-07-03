package com.jelly.thor.crashhelp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

/**
 * 类描述：bean类<br/>
 * 创建人：吴冬冬<br/>
 * 创建时间：2018/7/3 14:23 <br/>
 */
@SuppressLint("ParcelCreator")// 用于处理 Lint 的错误提示
@Parcelize
/**
 * @param  ex 崩溃主要信息
 * @param packageName 包名
 * @param exceptionMsg 崩溃主要信息
 * @param className 崩溃类名
 * @param fileName 崩溃文件名
 * @param methodName 崩溃方法
 * @param lineNum 崩溃行数
 * @param exceptionType 崩溃类型
 * @param allExceptionMsg 全部崩溃信息
 * @param time 崩溃时间
 * @param device 设备信息
 */
data class CrashBean(val ex: Throwable,
                     val packageName: String,
                     val exceptionMsg: String,
                     val className: String,
                     val fileName: String,
                     val methodName: String,
                     val lineNum: Int,
                     val exceptionType: String,
                     val allExceptionMsg: String,
                     val time: Long,
                     val device: Device = Device()) : Parcelable {

    @SuppressLint("ParcelCreator")
    @Parcelize
    /**
     * @param model 设备名
     * @param brand 设备厂商
     * @param version 系统版本号
     */
    data class Device(val model: String = Build.MODEL,
                      val brand: String = Build.BRAND,
                      val version: String = Build.VERSION.SDK_INT.toString()) : Parcelable
}