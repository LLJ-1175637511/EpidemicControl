package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentDiagnosisBinding

class DiagnosisFragment : BaseFragment<FragmentDiagnosisBinding>() {

    override fun getLayoutId() = R.layout.fragment_diagnosis

    private val vm by activityViewModels<MainVM>()

    override fun initCreate() {
        super.initCreate()
    }

    override fun initCreateView() {
        super.initCreateView()

    }

}