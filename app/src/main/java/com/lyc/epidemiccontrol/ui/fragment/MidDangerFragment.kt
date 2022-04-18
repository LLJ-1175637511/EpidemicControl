package com.lyc.epidemiccontrol.ui.fragment

import androidx.fragment.app.viewModels
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.DiagnosisVM
import com.lyc.epidemiccontrol.databinding.FragmentMidDangerBinding
import com.lyc.epidemiccontrol.ui.adapter.DangerAreaRV
import com.lyc.epidemiccontrol.utils.ToastUtils

class MidDangerFragment : BaseFragment<FragmentMidDangerBinding>() {

    override fun getLayoutId() = R.layout.fragment_mid_danger

    private val vm by viewModels<DiagnosisVM>()

    private lateinit var adapter: DangerAreaRV

    override fun initCreate() {
        super.initCreate()
        vm.getMidDangerArea()
        adapter = DangerAreaRV(mutableListOf())
    }

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.recyclerView.adapter = adapter
        vm.midDangerBean.observe(this) {
            if (it.isEmpty()) {
                ToastUtils.toastShort("未查询到结果")
                return@observe
            }
            adapter.update(it)
        }
        mDataBinding.tvFilter.setOnClickListener {
            hideKeyBoard()
            val list = vm.filterMidList(mDataBinding.etFilterContent.text.toString())
            vm.midDangerBean.postValue(list)
        }
    }
}