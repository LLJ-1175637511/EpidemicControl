package com.lyc.epidemiccontrol.ui.activity

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.lyc.epidemiccontrol.ext.save
import com.lyc.epidemiccontrol.net.NetActivity
import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib
import com.lyc.epidemiccontrol.utils.ToastUtils

abstract class BaseLoginActivity<DB : ViewDataBinding> : NetActivity<DB>() {

    override fun init() {
        super.init()
        //检查权限
        checkPermission()
        //检查用户名密码
        loadUserData()
    }

    private fun checkPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                initPermission(),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    abstract fun initPermission(): Array<String>

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                ToastUtils.toastShort("权限未允许")
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = initPermission().all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    /**
     * ----------------------------------------------------------------------------------------------
     * */

    private var mUserName = ""
    private var mPassWord = ""

    fun getUserInfo() = Pair(mUserName, mPassWord)

    private fun loadUserData() {
        ECLib.getSP(Const.SPUser).let { sp ->
            if (sp.contains(Const.SPUserID)) {
                mUserName = sp.getString(Const.SPUserID, "").toString()
            }
            if (sp.contains(Const.SPUserPwd)) {
                mPassWord = sp.getString(Const.SPUserPwd, "").toString()
            }
        }
    }

    /**
     * 保存用户名 密码
     */
    fun savedUserPwdSp(userId:String,pwd: String) {
        ECLib.getSP(Const.SPUser).save {
            putString(Const.SPUserPwd, pwd)
            putString(Const.SPUserID, userId)
        }
    }

}