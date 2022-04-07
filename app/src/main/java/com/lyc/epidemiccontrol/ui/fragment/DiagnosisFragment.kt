package com.lyc.epidemiccontrol.ui.fragment

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
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
        initMainView()
        initTab()
    }

    private fun initMainView() {
        vm.casesBean.observe(viewLifecycleOwner) { ccb ->
            ccb?.let {
                mDataBinding.diagnosis = it
                val t = "${it.highDangerCount} / ${it.midDangerCount}"
                mDataBinding.tvHighMidArea.text = t
            }

        }
    }

    private fun initTab() {
        mDataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.customView == null) {
                    tab.setCustomView(R.layout.tab_text)
                }
                val t = tab.customView?.findViewById<TextView>(R.id.tab_text)
                t?.setTextAppearance(R.style.TabLayoutTextSelected)
                t?.text = tabList[tab.position]
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.customView == null) {
                    tab.setCustomView(R.layout.tab_text)
                }
                val t = tab.customView?.findViewById<TextView>(R.id.tab_text)
                t?.setTextAppearance(R.style.TabLayoutTextUnSelected)
                t?.text = tabList[tab.position]
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        tabList.forEach {
            val newTab = mDataBinding.tabLayout.newTab()
            newTab.text = it
            mDataBinding.tabLayout.addTab(newTab)
        }
        mDataBinding.viewPager.adapter = object :
            FragmentPagerAdapter(requireActivity().supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int {
                return tabList.size
            }

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> HighDangerFragment.instance()
                    1 -> MidDangerFragment.instance()
                    else -> Fragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return tabList[position]
            }
        }
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager, false);
    }


}