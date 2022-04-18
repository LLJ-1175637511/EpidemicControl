package com.lyc.epidemiccontrol.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.vm.DiagnosisVM
import com.lyc.epidemiccontrol.databinding.FragmentDiagnosisBinding
import com.lyc.epidemiccontrol.net.NetFragment

class DiagnosisFragment : NetFragment<FragmentDiagnosisBinding>() {

    override fun getLayoutId() = R.layout.fragment_diagnosis

    private val vm by activityViewModels<DiagnosisVM>()

    private val tabList = arrayOf("高风险地区", "中风险地区")

    override fun initCreate() {
        super.initCreate()
        vm.getCases()
    }

    override fun initCreateView() {
        super.initCreateView()
        initTab()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "高中风险地区"
        vm.casesBean.observe(viewLifecycleOwner) { ccb ->
            ccb?.let {
                mDataBinding.diagnosis = it
                val t = "${it.highDangerCount} / ${it.midDangerCount}"
                mDataBinding.tvHighMidArea.text = t
            }

        }
    }

    class PageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) HighDangerFragment() else MidDangerFragment()
        }
    }

    @SuppressLint("NewApi")
    private fun initTab() {
        fun updateCustomView(tab: TabLayout.Tab, isSelect: Boolean, position: Int) {
            if (tab.customView == null) {
                tab.setCustomView(R.layout.tab_text)
            }
            tab.customView?.findViewById<TextView>(R.id.tab_text)?.apply {
                text = tabList[position]
                if (isSelect) {
                    setTextAppearance(R.style.TabLayoutTextSelected)
                } else {
                    setTextAppearance(R.style.TabLayoutTextUnSelected)
                }
            }
        }
        mDataBinding.viewPager.adapter = PageAdapter(requireActivity())
        TabLayoutMediator(mDataBinding.tabLayout, mDataBinding.viewPager) { tab, position ->
            if (position == 0) {
                updateCustomView(tab, true, position)
            }
            tab.text = tabList[position]
        }.attach()
        mDataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateCustomView(tab, true, tab.position)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateCustomView(tab, false, tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }


}