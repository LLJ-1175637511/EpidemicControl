package com.lyc.epidemiccontrol.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityBackHomeBinding
import com.lyc.epidemiccontrol.net.NetActivity
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.launch

class BackHomeActivity : NetActivity<ActivityBackHomeBinding>() {

    override fun getLayoutId() = R.layout.activity_back_home

    private var uri: Uri? = null

    @SuppressLint("CheckResult")
    private val launchPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (ar.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            uri = ar.data?.data ?: return@registerForActivityResult
            Glide.with(this).load(uri).into(mDataBinding.ivPhoto)
        }

    private val launchPhotoPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager())) {
                ToastUtils.toastShort("存储权限获取失败")
                finish()
                return@registerForActivityResult
            }
        }

    override fun init() {
        super.init()
        initMainView()
        requestPermission()
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "返乡上报"
        mDataBinding.ivPhoto.setOnClickListener {
            choosePhoto()
        }
        mDataBinding.btSelectPhoto.setOnClickListener {
            reportPhoto()
        }
    }

    private fun reportPhoto() {
        lifecycleScope.launch {
//            fastRequest<String> {
//                SystemRepository.reportPhoto()
//            }
        }
    }

    private fun choosePhoto() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
        galleryIntent.type = "image/*"
        launchPhoto.launch(galleryIntent)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //Android11（SDK版本30）
            // 先判断有没有权限
            if (!Environment.isExternalStorageManager()) { //判断是否获取到“允许管理所有文件”权限
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + getPackageName())
                launchPhotoPermission.launch(intent)
            } else { //没有获取到“允许管理所有文件”权限
                //请求“允许管理所有文件”权限
                ToastUtils.toastShort("存储权限获取失败")
                finish()
            }
        }
    }

}