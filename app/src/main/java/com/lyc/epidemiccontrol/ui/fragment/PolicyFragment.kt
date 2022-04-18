package com.lyc.epidemiccontrol.ui.fragment

import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.PolicyBean
import com.lyc.epidemiccontrol.data.bean.ScienceBean
import com.lyc.epidemiccontrol.databinding.FragmentPolicyBinding
import com.lyc.epidemiccontrol.net.NetFragment
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.ui.adapter.ScienceRV
import kotlinx.coroutines.launch

class PolicyFragment : NetFragment<FragmentPolicyBinding>() {

    override fun getLayoutId() = R.layout.fragment_policy

    private var isUpPolicy = false
    private var isUpScience = true

    override fun initCreateView() {
        super.initCreateView()
        getData()
        mDataBinding.tvPolicy.apply {
            setOnClickListener {
                val p = mDataBinding.lvPolicy.layoutParams
                val res = if (isUpPolicy){
                    mDataBinding.lvPolicy.layoutParams = LinearLayout.LayoutParams(p.width,p.height,0f)
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)
                }else{
                    mDataBinding.lvPolicy.layoutParams = LinearLayout.LayoutParams(p.width,p.height,1f)
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_up_24)
                }
                isUpPolicy = !isUpPolicy
                mDataBinding.tvPolicy.setCompoundDrawablesWithIntrinsicBounds(null,null, res,null)
            }
        }
        mDataBinding.tvScience.apply {
            setOnClickListener {
                val p = mDataBinding.rvScience.layoutParams
                val res = if (isUpScience){
                    mDataBinding.rvScience.layoutParams = LinearLayout.LayoutParams(p.width,p.height,0f)
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)
                }else{
                    mDataBinding.rvScience.layoutParams = LinearLayout.LayoutParams(p.width,p.height,2f)
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_up_24)
                }
                isUpScience = !isUpScience
                mDataBinding.tvScience.setCompoundDrawablesWithIntrinsicBounds(null,null, res,null)
            }
        }

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
                mDataBinding.lvPolicy.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pList)
            }
            fastRequest<List<ScienceBean>> {
                SystemRepository.getScienceInfo()
            }?.let {
                mDataBinding.rvScience.adapter = ScienceRV(it)
            }
        }
    }

}