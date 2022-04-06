package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.leaf.library.StatusBarUtil
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentManagerBinding
import com.lyc.epidemiccontrol.ui.activity.AppointAddActivity

class ManagerFragment : BaseFragment<FragmentManagerBinding>() {

    override fun getLayoutId() = R.layout.fragment_manager

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.ivYuYue.setOnClickListener {
            startCommonActivity<AppointAddActivity>()
        }

    }

}