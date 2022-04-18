package com.lyc.epidemiccontrol.ui.fragment

import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.PolicyBean
import com.lyc.epidemiccontrol.data.bean.ScienceBean
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.FragmentPolicyBinding
import com.lyc.epidemiccontrol.net.NetFragment
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import kotlinx.coroutines.launch

class PolicyFragment : NetFragment<FragmentPolicyBinding>() {

    override fun getLayoutId() = R.layout.fragment_policy

    private val sList = mutableListOf<ScienceBean>()

    override fun initCreate() {
        super.initCreate()
    }

    override fun initCreateView() {
        super.initCreateView()
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            fastRequest<List<PolicyBean>> {
                SystemRepository.getTopPolicy()
            }?.let {
                val pList = mutableListOf<String>()
                it.forEachIndexed { index, policyBean ->
                    pList.add("${index + 1}、${policyBean.content}")
                }
                mDataBinding.lvScience.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pList)
            }
            fastRequest<List<ScienceBean>> {
                SystemRepository.getScienceInfo()
            }?.let {
                sList.addAll(it)
            }
        }
    }

}