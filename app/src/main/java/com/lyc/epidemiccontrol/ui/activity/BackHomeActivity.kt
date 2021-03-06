package com.lyc.epidemiccontrol.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityBackHomeBinding
import com.lyc.epidemiccontrol.databinding.DialogAppointDateBinding
import com.lyc.epidemiccontrol.net.NetActivity
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.TimeEnum
import com.lyc.epidemiccontrol.utils.ToastUtils
import com.lyc.epidemiccontrol.utils.addZero
import com.lyc.epidemiccontrol.utils.convertLongTime
import kotlinx.coroutines.delay
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
        mDataBinding.time.text = INIT_TIME
        mDataBinding.tvReChoose.setOnClickListener {
            choosePhoto()
        }
        mDataBinding.btSelectPhoto.setOnClickListener {
            reportPhoto()
        }
        mDataBinding.time.setOnClickListener {
            showDatePicker()
        }
    }

    private fun reportPhoto() {
        val u = uri
        if (u == null) {
            ToastUtils.toastShort("未选择图片 或 图片无更新")
            return
        }
        val time = mDataBinding.time.text.toString()
        if (time == INIT_TIME) {
            ToastUtils.toastShort("未选择返乡时间")
            return
        }
        lifecycleScope.launch {
            fastRequest<String> {
                SystemRepository.reportPhoto(
                    SysNetConfig.reportBackHomePhoto(this@BackHomeActivity, u),
                    SysNetConfig.reportBackHomeText(time)
                )
            }?.let {
                ToastUtils.toastShort("上传成功")
                delay(1000)
                finish()
            }
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
            }
        }
    }

    private fun showDatePicker() {
        val binding = DialogAppointDateBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.setCancelable(false)
        binding.btSure.setOnClickListener {
            val t =
                "${binding.datePicker.year}-${(binding.datePicker.month + 1).addZero()}-${binding.datePicker.dayOfMonth}"
            mDataBinding.time.text = t
            if (t.convertLongTime(TimeEnum.YYbMMbDD) < System.currentTimeMillis() - 1000 * 60 * 60 * 24) {
                ToastUtils.toastShort("请选择未来的时间")
                return@setOnClickListener
            }
            dialog.cancel()
        }
        dialog.show()
    }

    companion object {
        private const val INIT_TIME = "设置预约时间"
    }
}