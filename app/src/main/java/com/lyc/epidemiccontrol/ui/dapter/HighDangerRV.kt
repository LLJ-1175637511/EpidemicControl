package com.lyc.epidemiccontrol.ui.dapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointQueryBean
import com.lyc.epidemiccontrol.data.bean.DangerAreaBean
import com.lyc.epidemiccontrol.databinding.ItemAppointQueryBinding
import com.lyc.epidemiccontrol.databinding.ItemDangerAreaBinding
import com.lyc.epidemiccontrol.utils.convertGeLinTime


class HighDangerRV(private val list: MutableList<DangerAreaBean>) :
    RecyclerView.Adapter<HighDangerRV.Holder>() {

    class Holder(val binding: ItemDangerAreaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: DangerAreaBean, b: Boolean) {
            binding.text.text = item.district
            val color = if (b) R.color.blue_low else R.color.red_low
            binding.text.setBackgroundResource(color)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemDangerAreaBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_danger_area,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position],position % 2 == 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(l: List<DangerAreaBean>) {
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}