package com.lyc.epidemiccontrol.net.repository

import com.lyc.epidemiccontrol.net.network.SystemNetWork
import okhttp3.MultipartBody
import okhttp3.RequestBody

object SystemRepository {

    suspend fun loginRequest(map: Map<String, String>) = SystemNetWork.login(map)

    suspend fun registerRequest(map: Map<String, String>) = SystemNetWork.register(map)

    suspend fun appointNewRequest(map: Map<String, String>) = SystemNetWork.appointNew(map)

    suspend fun appointQueryRequest() = SystemNetWork.appointQuery()

    suspend fun appointNewYiMiaoRequest(map: Map<String, String>) = SystemNetWork.appointNewYiMiao(map)

    suspend fun appointQueryYiMiaoRequest() = SystemNetWork.appointQueryYiMiao()

    suspend fun getCases() = SystemNetWork.getCases()

    suspend fun getHighDangerArea() = SystemNetWork.getHighDangerArea()

    suspend fun getMidDangerArea() = SystemNetWork.getMidDangerArea()

    suspend fun reportPhoto(photo: MultipartBody.Part, map: Map<String, RequestBody>) = SystemNetWork.reportPhoto(photo,map)



}