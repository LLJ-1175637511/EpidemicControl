package com.lyc.epidemiccontrol.data.bean

data class ConfirmedCasesBean(
    val confirmedCount: Int,
    val confirmedIncr: Int,
    val createTime: String,
    val curedCount: Int,
    val curedIncr: Int,
    val currentConfirmedCount: Int,
    val currentConfirmedIncr: Int,
    val deadCount: Int,
    val deadIncr: Int,
    val highDangerCount: Int,
    val midDangerCount: Int,
    val modifyTime: String,
    val seriousCount: Int,
    val seriousIncr: Int,
    val suspectedCount: Int,
    val suspectedIncr: Int,
    val yesterdayConfirmedCountIncr: Int,
    val yesterdaySuspectedCountIncr: Int
)

