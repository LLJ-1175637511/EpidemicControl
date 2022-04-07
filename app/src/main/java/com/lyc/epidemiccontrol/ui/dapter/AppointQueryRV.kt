package com.lyc.epidemiccontrol.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointQueryBean
import com.lyc.epidemiccontrol.databinding.ItemAppointQueryBinding
import com.lyc.epidemiccontrol.utils.convertGeLinTime


class AppointQueryRV(private val list: List<AppointQueryBean.PageData>) : RecyclerView.Adapter<AppointQueryRV.Holder>() {

    class Holder(val binding: ItemAppointQueryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item:AppointQueryBean.PageData){
            binding.tvUserNum.text = "用户：${item.userNum}"
            binding.tvPhone.text = "手机号：${item.telephone}"
            binding.tvAppointAddress.text = "核酸预约地点：${item.appintSite}"
            binding.tvIdCard.text = "身份证号：${item.identityNum.toString()}"
            binding.tvAppointDate.text = "核酸检测时间：${item.appintDate.convertGeLinTime()}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemAppointQueryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_appoint_query,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount() = list.size

}