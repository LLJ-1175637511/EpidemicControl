package com.lyc.epidemiccontrol.ui.activity

import android.text.InputType
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityAppointHesuanBinding
import com.lyc.epidemiccontrol.databinding.DialogAppointDateBinding
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.ext.save
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.ui.dialog.AppointAreaDialog
import com.lyc.epidemiccontrol.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointHeSuanActivity : BaseActivity<ActivityAppointHesuanBinding>() {

    override fun getLayoutId() = R.layout.activity_appoint_hesuan

    private var date = System.currentTimeMillis().convertTime(TimeEnum.YYbMMbDD)

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "核酸预约"
        mDataBinding.address.setText(INIT_AREA)
        mDataBinding.address.inputType = InputType.TYPE_NULL
        mDataBinding.btDate.setText(date)
        mDataBinding.btDate.inputType = InputType.TYPE_NULL
        mDataBinding.btDate.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.tvAppoint.setOnClickListener {
            if (mDataBinding.address.text.toString() == INIT_AREA) {
                ToastUtils.toastShort("请先选择预约地点")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val b = SystemRepository.appointNewRequest(
                    SysNetConfig.buildAppointNewMap(
                        creatDate = System.currentTimeMillis().convertTime(TimeEnum.THIS),
                        telephone = mDataBinding.phone.text.trim().toString(),
                        appintDate = date,
                        appintSite = mDataBinding.address.text.trim().toString(),
                        identityNum = mDataBinding.idCard.text.trim().toString()
                    )
                )
                if (b.code.isCodeSuc()) {
                    ECLib.getSP(Const.SPUser).save {
                        putString(Const.SPUserIDCard, mDataBinding.idCard.text.toString())
                    }
                    LogUtils.d("Appiont", "suc")
                    ToastUtils.toastShort("预约成功")
                    delay(1500)
                    finish()
                } else {
                    ToastUtils.toastShort("预约失败，${b.message}")
                    LogUtils.d("Appiont", "预约失败，${b.message}")
                }
            }
        }
        ECLib.getUB()?.let {
            mDataBinding.phone.setText(it.telephone)
        }
        mDataBinding.idCard.setText(ECLib.getSP(Const.SPUser).getString(Const.SPUserIDCard, ""))
        mDataBinding.address.setOnClickListener {
            showDialog(
                AppointAreaDialog(AppointAreaDialog.Companion.AppointType.HeSuan) {
                    mDataBinding.address.setText(it)
                }, "AppointHeSuanArea"
            )
        }
    }

    private fun showDatePicker() {
        val binding = DialogAppointDateBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.setCancelable(false)
        binding.btSure.setOnClickListener {
            date =
                "${binding.datePicker.year}-${(binding.datePicker.month + 1).addZero()}-${binding.datePicker.dayOfMonth}"
            dialog.cancel()
        }
        dialog.show()
    }

    companion object {
        private const val INIT_AREA = "设置预约地点"
    }
}