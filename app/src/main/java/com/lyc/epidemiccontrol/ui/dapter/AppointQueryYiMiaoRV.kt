package com.lyc.epidemiccontrol.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointQueryYiMiaoBean
import com.lyc.epidemiccontrol.databinding.ItemAppointQueryYimiaoBinding
import com.lyc.epidemiccontrol.utils.convertGeLinTime


class AppointQueryYiMiaoRV(private val list: List<AppointQueryYiMiaoBean.PageData>) : RecyclerView.Adapter<AppointQueryYiMiaoRV.Holder>() {

    class Holder(val binding: ItemAppointQueryYimiaoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item:AppointQueryYiMiaoBean.PageData){
            binding.tvUserNum.text = "用户：${item.userNum}"
            binding.tvPhone.text = "手机号：${item.telephone}"
            binding.tvAppointAddress.text = "疫苗接种地点：${item.appintSite}"
            binding.tvIdCard.text = "身份证号：${item.identityNum.toString()}"
            binding.tvAppointDate.text = "疫苗检测时间：${item.appintDate.convertGeLinTime()}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemAppointQueryYimiaoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_appoint_query_yimiao,
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