package com.lyc.epidemiccontrol.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    abstract fun getLayoutId(): Int

    lateinit var mDataBinding: DB

    open fun init() {}

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        init()
    }

    fun <T : Activity> startCommonActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
    }

    inline fun <reified T : Activity> startCommonActivity() {
        startCommonActivity(T::class.java)
    }

    fun <T : Activity> startActivityAndFinish(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }

    inline fun <reified T : Activity> startActivityAndFinish() {
        startCommonActivity(T::class.java)
        finish()
    }

    private fun initView() {
        mDataBinding = DataBindingUtil.setContentView<DB>(this, getLayoutId())
        mDataBinding.lifecycleOwner = this //初始化生命周期
    }

    fun hideKeyBoard(){
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁后 解绑dataBinding
        if (this::mDataBinding.isInitialized) mDataBinding.unbind()
    }

    fun showDialog(df: DialogFragment, tag: String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val prev: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        df.show(ft, tag)
    }

}