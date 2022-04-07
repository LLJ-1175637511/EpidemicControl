package com.lyc.epidemiccontrol.net.config

import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib

object SysNetConfig {

    const val UserNum = "UserNum" //用户名
    const val UserPwd = "UserPwd" //用户密码
    const val UserName = "UserName"
    const val Gender = "Gender"
    const val Address = "Address"
    const val Telephone = "Telephone"
    const val Eamil = "Eamil"
    const val CreatDate = "CreatDate"
    const val IdentityNum = "IdentityNum"
    const val AppintDate = "AppintDate"
    const val AppintSite = "AppintSite"
    const val AppintType = "AppintType"

    const val MULTIPART_TEXT = "text/plain"
    const val MULTIPART_FILE = "multipart/form-data"

    fun buildLoginMap(
        user: String,
        pass: String
    ) = mapOf(UserNum to user, UserPwd to pass)

    fun buildRegisterMap(
        username: String,
        userNumber: String,
        password: String,
        email: String,
        phone: String,
        address: String,
        sex: String
    ) = mapOf(
        UserName to username,
        UserNum to userNumber,
        UserPwd to password,
        Eamil to email,
        Telephone to phone,
        Address to address,
        Gender to sex,
    )

    fun buildAppointNewMap(
        creatDate: String,
        telephone: String,
        appintDate: String,
        appintSite: String,
        identityNum : String
    ) = mapOf(
        UserNum to getUserId(),
        CreatDate to creatDate,
        Telephone to telephone,
        AppintDate to appintDate,
        AppintSite to appintSite,
        IdentityNum to identityNum
    )

    fun buildAppointNewYiMiaoMap(
        appintType: String,
        telephone: String,
        appintDate: String,
        appintSite: String,
        identityNum : String
    ) = mapOf(
        UserNum to getUserId(),
        Telephone to telephone,
        AppintDate to appintDate,
        AppintSite to appintSite,
        IdentityNum to identityNum,
        AppintType to appintType
    )

    fun getUserId() = ECLib.getSP(Const.SPUser).getString(Const.SPUserID,"").toString()

    fun getAuth() = "Bearer ${
        ECLib.getSP(Const.SPNet).getString(
            Const.SPNetToken, ""
        ).toString()
    }"


}