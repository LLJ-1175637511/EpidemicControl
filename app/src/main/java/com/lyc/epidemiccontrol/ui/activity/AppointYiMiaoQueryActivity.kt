package com.lyc.epidemiccontrol.ui.activity

import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointQueryBean
import com.lyc.epidemiccontrol.data.bean.AppointQueryYiMiaoBean
import com.lyc.epidemiccontrol.databinding.ActivityAppointQueryBinding
import com.lyc.epidemiccontrol.databinding.ActivityAppointQueryYimiaoBinding
import com.lyc.epidemiccontrol.net.NetActivity
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.ui.dapter.AppointQueryRV
import com.lyc.epidemiccontrol.ui.dapter.AppointQueryYiMiaoRV
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.launch

class AppointYiMiaoQueryActivity : NetActivity<ActivityAppointQueryYimiaoBinding>() {

    override fun getLayoutId() = R.layout.activity_appoint_query_yimiao

    private lateinit var adapter:AppointQueryYiMiaoRV

    override fun init() {
        super.init()
        initMainView()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            fastRequest<AppointQueryYiMiaoBean> {
                SystemRepository.appointQueryYiMiaoRequest()
            }?.let {
                if (it.pageData.isEmpty()) {
                    ToastUtils.toastShort("未查询到预约记录")
                    return@let
                }
                adapter = AppointQueryYiMiaoRV(it.pageData)
                mDataBinding.recyclerView.adapter = adapter
            }
        }
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "疫苗预约记录查询"
    }

}