package com.lyc.epidemiccontrol.ui.fragment

import android.content.Intent
import com.bumptech.glide.Glide
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.FragmentUserBinding
import com.lyc.epidemiccontrol.ui.activity.LoginActivity
import com.lyc.epidemiccontrol.utils.ECLib

class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun getLayoutId() = R.layout.fragment_user

    override fun initCreateView() {
        super.initCreateView()
        val userBean = ECLib.getUB()
        userBean?.let {
            mDataBinding.toolbar.toolbarBaseTitle.text = "个人资料"
            mDataBinding.tvEmail.text = "邮箱：${it.eamil}"
            mDataBinding.tvPhone.text = "电话：${it.telephone}"
            mDataBinding.tvInfo.text =
                "${it.gender}    ${(21..23).random()}   |   学生   |   ${it.address}"
            mDataBinding.tvNum.text = "现居住地：${it.address}"
            mDataBinding.tvUserName.text = it.userName
            val path = "http://39.105.114.212:8080${it.headPortrait}"
            Glide.with(this).load(path).into(mDataBinding.ivHead)
        }

        mDataBinding.tvQuit.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

}