package com.lyc.epidemiccontrol.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityAppointYimiaoBinding
import com.lyc.epidemiccontrol.databinding.DialogAppointDateBinding
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.ext.save
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.ui.dialog.AppointAreaDialog
import com.lyc.epidemiccontrol.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointYiMiaoActivity : BaseActivity<ActivityAppointYimiaoBinding>() {

    override fun getLayoutId() = R.layout.activity_appoint_yimiao

    private var date = System.currentTimeMillis().convertTime(TimeEnum.YYbMMbDD)

    private var mAppointType = 1

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "疫苗预约"
        mDataBinding.address.setText(INIT_AREA)
        mDataBinding.btDate.setText(date)
        ECLib.getUB()?.let {
            mDataBinding.phone.setText(it.telephone)
        }
        mDataBinding.idCard.setText(ECLib.getSP(Const.SPUser).getString(Const.SPUserIDCard, ""))

        mDataBinding.btDate.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.tvAppoint.setOnClickListener {
            lifecycleScope.launch {
                if (mDataBinding.address.text.toString() == INIT_AREA) {
                    ToastUtils.toastShort("请先选择预约地点")
                    return@launch
                }
                val b = SystemRepository.appointNewYiMiaoRequest(
                    SysNetConfig.buildAppointNewYiMiaoMap(
                        appintType = mAppointType.toString(),
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
        mDataBinding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radio1 -> mAppointType = 1
                R.id.radio2 -> mAppointType = 2
                R.id.radio3 -> mAppointType = 3
            }
        }
        mDataBinding.address.setOnClickListener {
            showDialog(
                AppointAreaDialog(AppointAreaDialog.Companion.AppointType.YiMiao) {
                    mDataBinding.address.setText(it)
                }, "AppointYiMiaoArea"
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
            mDataBinding.btDate.setText(date)
            dialog.cancel()
        }
        dialog.show()
    }

    companion object {
        private const val INIT_AREA = "设置预约地点"
    }
}