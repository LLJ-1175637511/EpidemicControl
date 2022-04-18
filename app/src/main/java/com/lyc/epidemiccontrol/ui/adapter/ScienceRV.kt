package com.lyc.epidemiccontrol.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.ScienceBean
import com.lyc.epidemiccontrol.databinding.ItemScienceBinding


class ScienceRV(private val list: List<ScienceBean>) :
    RecyclerView.Adapter<ScienceRV.Holder>() {

    class Holder(val binding: ItemScienceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: ScienceBean) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemScienceBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_science,
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