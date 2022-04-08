package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val Auth = "Authorization"

interface AppointServer {

    @POST("AppointNucleicAcid/AddAppointVaccine")
    fun appointNew(
        @QueryMap map: Map<String, String>,
        @Header(Auth) auth: String,
    ): Call<BaseBean>

    @POST("AppointVaccine/AddAppointVaccine")
    fun appointNewYiMiao(
        @QueryMap map: Map<String, String>,
        @Header(Auth) auth: String,
    ): Call<BaseBean>

    @GET("AppointNucleicAcid/GetAppointVaccineInfo")
    fun appointQuery(
        @Query(SysNetConfig.UserNum) userNum: String,
        @Header(Auth) auth: String,
    ): Call<BaseBean>

  @GET("AppointVaccine/GetAppointVaccineInfo")
    fun appointQueryYiMiao(
        @Query(SysNetConfig.UserNum) userNum: String,
        @Header(Auth) auth: String,
    ): Call<BaseBean>

    @Multipart
    @POST("AppointVaccine/GetAppointVaccineInfo")
    fun reportPhoto(
        @Header(Auth) auth: String,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part photo: MultipartBody.Part
    ): Call<BaseBean>

}