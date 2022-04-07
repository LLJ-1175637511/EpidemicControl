package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface UserServer {

    @GET("Login/GetLoginUser")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

    @POST("Account/CreateUser")
    fun register(@QueryMap map: Map<String, String>): Call<BaseBean>

}