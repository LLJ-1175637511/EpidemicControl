package com.lyc.epidemiccontrol.ui.activity

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.leaf.library.StatusBarUtil
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.ActivityMainBinding
import com.lyc.epidemiccontrol.utils.LogUtils


open class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val vm by viewModels<MainVM>()

    override fun init() {
        super.init()
        initBar()
        initNav()
        initData()
    }

    @SuppressLint("ResourceAsColor")
    private fun initBar() {
        vm.barColor.observe(this){
            if (it){
                hideSystemUI()
                mDataBinding.container.setBackgroundResource(R.mipmap.bg)
//                StatusBarUtil.setTransparentForWindow(this)

                LogUtils.d("main_bar","true")
            }else{
                mDataBinding.container.setBackgroundResource(R.color.common_bg)
                showSystemUI()
                StatusBarUtil.setColor(this,R.color.colorPrimary)
            }
        }
    }

    private fun initData() {
        intent.getParcelableExtra<LoginBean>(LoginActivity.userInfo)?.let {
            vm.userInfo.postValue(it)
        }
    }

    private fun initNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        mDataBinding.navView.setupWithNavController(navController)
    }
}