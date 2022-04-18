package com.lyc.epidemiccontrol.net.config

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib
import com.lyc.epidemiccontrol.utils.PhotoUtils
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
    const val HealthProve = "HealthProve"
    const val site = "site"
    const val BackHomeTime = "BackHomeTime"

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
        identityNum: String
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
        identityNum: String
    ) = mapOf(
        UserNum to getUserId(),
        Telephone to telephone,
        AppintDate to appintDate,
        AppintSite to appintSite,
        IdentityNum to identityNum,
        AppintType to appintType
    )

    fun getUserId() = ECLib.getSP(Const.SPUser).getString(Const.SPUserID, "").toString()

    fun getAuth() = "Bearer ${
        ECLib.getSP(Const.SPNet).getString(
            Const.SPNetToken, ""
        ).toString()
    }"

    /**
     * 构造核查上传图片的文本PartMap
     */
    fun reportBackHomeText(
        time: String
    ): Map<String, String> = mapOf(
        BackHomeTime to time,
        UserNum to getUserId()
    )

    suspend fun reportBackHomePhoto(context: Context, uri: Uri): MultipartBody.Part {
        val path = PhotoUtils.getFileAbsolutePath(context, uri)
        val faceFile = File(path)

        if (!faceFile.exists()) throw Exception("图片缺失")

        val compressedImageFile = Compressor.compress(context, faceFile) {
            quality(50)
            format(Bitmap.CompressFormat.JPEG)
            size(512_152) // 512kb
        }

        val fmt = MediaType.parse(MULTIPART_FILE)

        val faceRequest = RequestBody.create(fmt, compressedImageFile)
        //注意字段名
        return MultipartBody.Part.createFormData(HealthProve, compressedImageFile.name, faceRequest)
    }


}