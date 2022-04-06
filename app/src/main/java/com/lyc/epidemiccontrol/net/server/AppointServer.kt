package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val Auth = "Authorization"

interface AppointServer {

    @POST("AppointNucleicAcid/AddAppointVaccine")
    fun appointNew(
        @QueryMap map: Map<String, String>,
        @Query(SysNetConfig.IdentityNum) idCard:Long,
        @Header(Auth) auth: String = SysNetConfig.getAuth(),
        ): Call<BaseBean>

}