package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserServer {

    @GET("Login/GetLoginUser")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

    @POST("Account/CreateUser")
    fun register(@QueryMap map: Map<String, String>): Call<BaseBean>

}