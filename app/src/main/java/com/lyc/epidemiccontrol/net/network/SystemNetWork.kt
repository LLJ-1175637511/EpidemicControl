package com.lyc.epidemiccontrol.net.network

import com.lyc.epidemiccontrol.net.RetrofitCreator
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.server.AppointServer
import com.lyc.epidemiccontrol.net.server.CasesServer
import com.lyc.epidemiccontrol.net.server.PolicyAndScienceServer
import com.lyc.epidemiccontrol.net.server.UserServer
import okhttp3.MultipartBody
import retrofit2.await

object SystemNetWork {

    private val userServer by lazy { RetrofitCreator.create<UserServer>() }

    private val appointServer by lazy { RetrofitCreator.create<AppointServer>() }

    private val casesServer by lazy { RetrofitCreator.create<CasesServer>() }

    private val policyAndScienceServer by lazy { RetrofitCreator.create<PolicyAndScienceServer>() }

    suspend fun login(map: Map<String, String>) = userServer.login(map).await()

    suspend fun register(map: Map<String, String>) = userServer.register(map).await()

    suspend fun appointNew(map: Map<String, String>) =
        appointServer.appointNew(map, SysNetConfig.getAuth()).await()

    suspend fun appointNewYiMiao(map: Map<String, String>) =
        appointServer.appointNewYiMiao(map, SysNetConfig.getAuth()).await()

    suspend fun appointQuery() =
        appointServer.appointQuery(SysNetConfig.getUserId(), SysNetConfig.getAuth()).await()

    suspend fun appointQueryYiMiao() =
        appointServer.appointQueryYiMiao(SysNetConfig.getUserId(), SysNetConfig.getAuth()).await()

    suspend fun reportPhoto(photo: MultipartBody.Part, map: Map<String, String>) =
        appointServer.reportPhoto(SysNetConfig.getAuth(),map,photo).await()

    suspend fun getCases() = casesServer.getCases(SysNetConfig.getAuth()).await()

    suspend fun getHighDangerArea() = casesServer.getHighDangerArea(SysNetConfig.getAuth()).await()

    suspend fun getMidDangerArea() = casesServer.getMidDangerArea(SysNetConfig.getAuth()).await()

    suspend fun getYiMiaoArea(site:String) = appointServer.appointQueryYiMiaoArea(site,SysNetConfig.getAuth()).await()

    suspend fun getHeSuanArea(site:String) = appointServer.appointQueryHeSuanArea(site,SysNetConfig.getAuth()).await()

    suspend fun getScienceInfo() = policyAndScienceServer.getScienceInfo(SysNetConfig.getAuth()).await()

    suspend fun getTopPolicy() = policyAndScienceServer.getTopPolicy(SysNetConfig.getAuth()).await()

}