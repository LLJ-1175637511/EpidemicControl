package com.lyc.epidemiccontrol.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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

    private fun initBar() {

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