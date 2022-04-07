package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.ConfirmedCasesBean
import com.lyc.epidemiccontrol.data.vm.DiagnosisVM
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentDiagnosisBinding
import com.lyc.epidemiccontrol.net.NetFragment
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import kotlinx.coroutines.launch

class DiagnosisFragment : NetFragment<FragmentDiagnosisBinding>() {

    override fun getLayoutId() = R.layout.fragment_diagnosis

    private val vm by viewModels<DiagnosisVM>()

    override fun initCreate() {
        super.initCreate()
    }

    override fun initCreateView() {
        super.initCreateView()

    }

}