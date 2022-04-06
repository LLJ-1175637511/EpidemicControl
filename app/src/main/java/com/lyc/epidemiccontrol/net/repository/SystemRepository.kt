package com.lyc.epidemiccontrol.net.repository

import com.lyc.epidemiccontrol.net.network.SystemNetWork

object SystemRepository {

    suspend fun loginRequest(map: Map<String, String>) = SystemNetWork.login(map)

    suspend fun registerRequest(map: Map<String, String>) = SystemNetWork.register(map)

    suspend fun appointNewRequest(map: Map<String, String>, idCard: Long) = SystemNetWork.appointNew(map,idCard)

}