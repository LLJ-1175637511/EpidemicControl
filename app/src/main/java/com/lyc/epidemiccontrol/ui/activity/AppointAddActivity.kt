package com.lyc.epidemiccontrol.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityAppointAddBinding
import com.lyc.epidemiccontrol.databinding.DialogAppointDateBinding
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointAddActivity : BaseActivity<ActivityAppointAddBinding>() {

    override fun getLayoutId() = R.layout.activity_appoint_add

    private var date = System.currentTimeMillis().convertTime(TimeEnum.YYbMMbDD)

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.btDate.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.tvAppoint.setOnClickListener {
            lifecycleScope.launch {
                val t = System.currentTimeMillis().convertTime(TimeEnum.YYbMMbDDHHvMMvSS)
                print(t)
                val phone = mDataBinding.idCard.text.toString().toLong()
                print(phone)
                val b = SystemRepository.appointNewRequest(
                    SysNetConfig.buildAppointNewMap(
                        t,
                        mDataBinding.phone.text.toString(),
                        date,
                        mDataBinding.address.text.toString(),
                    ),
                    phone
                )
                if (b.code.isCodeSuc()) {
                    LogUtils.d("Appiont", "suc")
                    delay(1500)
                    finish()
                } else {
                    LogUtils.d("Appiont", "预约失败，${b.message}")
                }
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
            date =
                "${binding.datePicker.year}年${binding.datePicker.month}月${binding.datePicker.dayOfMonth}"
            dialog.cancel()
        }
        dialog.show()
    }

}