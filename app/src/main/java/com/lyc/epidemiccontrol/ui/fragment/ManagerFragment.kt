package com.lyc.epidemiccontrol.ui.fragment

import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.FragmentManagerBinding
import com.lyc.epidemiccontrol.ui.activity.*

class ManagerFragment : BaseFragment<FragmentManagerBinding>() {

    override fun getLayoutId() = R.layout.fragment_manager

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.ivAppoint.setOnClickListener {
            startCommonActivity<AppointHeSuanActivity>()
        }
        mDataBinding.ivAppointQuery.setOnClickListener {
            startCommonActivity<AppointHeSuanQueryActivity>()
        }
        mDataBinding.ivAppointYiMiao.setOnClickListener {
            startCommonActivity<AppointYiMiaoActivity>()
        }
        mDataBinding.ivAppointYiMiaoQuery.setOnClickListener {
            startCommonActivity<AppointYiMiaoQueryActivity>()
        }
        mDataBinding.ivBackHome.setOnClickListener {
            startCommonActivity<BackHomeActivity>()
        }
    }

}