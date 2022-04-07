package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.activityViewModels
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.DiagnosisVM
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentHighDangerBinding
import com.lyc.epidemiccontrol.databinding.FragmentPolicyBinding
import com.lyc.epidemiccontrol.ui.dapter.HighDangerRV
import com.lyc.epidemiccontrol.utils.ToastUtils

class HighDangerFragment private constructor() : BaseFragment<FragmentHighDangerBinding>() {

    override fun getLayoutId() = R.layout.fragment_high_danger

    private val vm by activityViewModels<DiagnosisVM>()

    private lateinit var adapter: HighDangerRV

    override fun initCreate() {
        super.initCreate()
        adapter = HighDangerRV(mutableListOf())
    }

    override fun onResume() {
        super.onResume()
        vm.getHighDangerArea()
    }

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.recyclerView.adapter = adapter
        vm.highDangerBean.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                ToastUtils.toastShort("未查询到结果")
                return@observe
            }
            adapter.update(it)
        }
        mDataBinding.tvFilter.setOnClickListener {
            hideKeyBoard()
            val list = vm.filterHighList(mDataBinding.etFilterContent.text.toString())
            vm.highDangerBean.postValue(list)
        }
    }

    companion object {
        private var instance: HighDangerFragment? = null
        fun instance(): HighDangerFragment {
            if (instance == null) instance = HighDangerFragment()
            return instance!!
        }
    }

}