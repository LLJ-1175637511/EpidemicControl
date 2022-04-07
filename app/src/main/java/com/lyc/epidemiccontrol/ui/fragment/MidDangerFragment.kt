package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.DiagnosisVM
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentHighDangerBinding
import com.lyc.epidemiccontrol.databinding.FragmentMidDangerBinding
import com.lyc.epidemiccontrol.databinding.FragmentPolicyBinding
import com.lyc.epidemiccontrol.ui.dapter.HighDangerRV

class MidDangerFragment private constructor(): BaseFragment<FragmentMidDangerBinding>() {

    override fun getLayoutId() = R.layout.fragment_mid_danger

    private val vm by activityViewModels<DiagnosisVM>()

    override fun initCreate() {
        super.initCreate()

    }

    override fun initCreateView() {
        super.initCreateView()

    }

    companion object {
        private var instance: MidDangerFragment? = null
        fun instance(): MidDangerFragment {
            if (instance == null) instance = MidDangerFragment()
            return instance!!
        }
    }

}