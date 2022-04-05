package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.leaf.library.StatusBarUtil
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentManagerBinding

class ManagerFragment : BaseFragment<FragmentManagerBinding>() {

    override fun getLayoutId() = R.layout.fragment_manager

    private val vm by activityViewModels<MainVM>()

    override fun initCreate() {
        vm.barColor.postValue(true)
        super.initCreate()
    }

    override fun initCreateView() {
        super.initCreateView()

    }

}