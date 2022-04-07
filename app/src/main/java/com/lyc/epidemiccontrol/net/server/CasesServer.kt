package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import retrofit2.Call
import retrofit2.http.*

private const val Auth = "Authorization"

interface CasesServer {

    @POST("ConfirmedCases/GetConfirmedCases")
    fun getCases(@Header(Auth) auth: String, ): Call<BaseBean>

}