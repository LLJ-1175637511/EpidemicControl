package com.lyc.epidemiccontrol.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.ext.save

@SuppressLint("StaticFieldLeak")
object ECLib {

    private lateinit var context: Context

    private var isInit = false

    @JvmStatic
    fun init(context:Context){
        if (isInit){
            throw IllegalArgumentException("请勿多次初始化")
        }
        this.context = context.applicationContext
    }

    fun getC() = context

    const val TAG = "EpidemicControl"

    fun getSP(key: String): SharedPreferences =
        context.getSharedPreferences(key, Context.MODE_PRIVATE)

    private val gson by lazy { Gson() }

    /*
    setUserBean
     */
    fun setUB(b: LoginBean){
        val sp = getSP(Const.SPUser)
        val newStr = gson.toJson(b)
        sp.save {
            putString(Const.SPUserBean,newStr)
        }
    }

    /*
    getUserBean
     */
    fun getUB(): LoginBean? {
        val sp = getSP(Const.SPUser)
        val dataStr = sp.getString(Const.SPUserBean, "")
        if (dataStr.isNullOrEmpty()) return null
        return gson.fromJson(dataStr, object : TypeToken<LoginBean>() {}.type)
    }

}