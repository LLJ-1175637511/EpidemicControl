package com.lyc.epidemiccontrol.ui.activity

import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointQueryBean
import com.lyc.epidemiccontrol.databinding.ActivityAppointQueryBinding
import com.lyc.epidemiccontrol.net.NetActivity
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.ui.adapter.AppointQueryRV
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.launch

class AppointHeSuanQueryActivity : NetActivity<ActivityAppointQueryBinding>() {

    override fun getLayoutId() = R.layout.activity_appoint_query

    private lateinit var adapter:AppointQueryRV

    override fun init() {
        super.init()
        initMainView()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            fastRequest<AppointQueryBean> {
                SystemRepository.appointQueryRequest()
            }?.let {
                val list = it.pageData
                if (list.isEmpty()) {
                    ToastUtils.toastShort("未查询到预约记录")
                    return@let
                }
                adapter = AppointQueryRV(list)
                mDataBinding.recyclerView.adapter = adapter
            }
        }
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "核酸预约记录查询"
    }

}