package com.lyc.epidemiccontrol.data.bean

data class AppointChooseAreaBean(
    val pageData: List<PageData>,
    val total: Int,
    val totalPage: Int
){
    data class PageData(
        val describe: String,
        val id: Int,
        val site: String,
        val telephone: String
    )
}

