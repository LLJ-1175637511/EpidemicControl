package com.lyc.epidemiccontrol.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityAppointHesuanBinding
import com.lyc.epidemiccontrol.databinding.DialogAppointDateBinding
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.ext.save
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
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
        mDataBinding.btDate.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.tvAppoint.setOnClickListener {
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
                        putString(Const.SPAppointYiMiaoAddress,mDataBinding.address.text.toString())
                        putString(Const.SPUserIDCard,mDataBinding.idCard.text.toString())
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
        mDataBinding.address.setText(ECLib.getSP(Const.SPUser).getString(Const.SPAppointYiMiaoAddress,""))
        mDataBinding.idCard.setText(ECLib.getSP(Const.SPUser).getString(Const.SPUserIDCard,""))

    }

    private fun showDatePicker() {
        val binding = DialogAppointDateBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.setCancelable(false)
        binding.btSure.setOnClickListener {
            date =
                "${binding.datePicker.year}年${binding.datePicker.month}月${binding.datePicker.dayOfMonth}"
            dialog.cancel()
        }
        dialog.show()
    }

}