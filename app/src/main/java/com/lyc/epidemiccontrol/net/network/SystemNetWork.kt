package com.lyc.epidemiccontrol.net.network

import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.net.RetrofitCreator
import com.lyc.epidemiccontrol.net.server.AppointServer
import com.lyc.epidemiccontrol.net.server.UserServer
import retrofit2.await

object SystemNetWork {

    private val userServer by lazy { RetrofitCreator.create<UserServer>() }

    private val appointServer by lazy { RetrofitCreator.create<AppointServer>() }

    suspend fun login(map: Map<String, String>) = userServer.login(map).await()

    suspend fun register(map: Map<String, String>) = userServer.register(map).await()

//    suspend fun appointNew(map: Map<String, String>,idCard: Long) = appointServer.appointNew(map,idCard).await()
    suspend fun appointNew(map: Map<String, String>,idCard: Long): BaseBean {
    println(map)
    print(idCard)
    return appointServer.appointNew(map,idCard).await()
}



}