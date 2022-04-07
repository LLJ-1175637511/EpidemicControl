package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val Auth = "Authorization"

interface CasesServer {

    @GET("ConfirmedCases/GetConfirmedCases")
    fun getCases(@Header(Auth) auth: String): Call<BaseBean>

    @GET("HigtDistrict/GetAllHigtDistricts")
    fun getHighDangerArea(@Header(Auth) auth: String): Call<BaseBean>

}