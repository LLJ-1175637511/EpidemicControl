package com.lyc.epidemiccontrol.data.bean

data class AppointQueryYiMiaoBean(
    val pageData: List<PageData>,
    val total: Int,
    val totalPage: Int
){
    data class PageData(
        val appintDate: String,
        val appintSite: String,
        val creatDate: String,
        val id: Int,
        val appintType: Int,
        val identityNum: Long,
        val telephone: String,
        val userNum: String
    )
}

