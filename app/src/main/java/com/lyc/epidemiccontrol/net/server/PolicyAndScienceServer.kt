package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

private const val Auth = "Authorization"

interface PolicyAndScienceServer {


    @GET("PolicyInfo/GetPolicyInfoListOfOnlyContent")
    fun getTopPolicy(@Header(Auth) auth: String): Call<BaseBean>

    @GET("SciencePopular/GetSciencePopularListOfOnlyRead")
    fun getScienceInfo(@Header(Auth) auth: String): Call<BaseBean>



}