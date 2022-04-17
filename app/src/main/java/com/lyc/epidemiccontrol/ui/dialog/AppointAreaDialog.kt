package com.lyc.epidemiccontrol.ui.dialog

import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.AppointChooseAreaBean
import com.lyc.epidemiccontrol.databinding.DialogChooseAreaBinding
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.launch

class AppointAreaDialog(private val type: AppointType, private val block: (area: String) -> Unit) :
    BaseDialog<DialogChooseAreaBinding>() {

    override fun getLayoutId() = R.layout.dialog_choose_area

    private val list = mutableListOf<String>()

    override fun initCreateView() {
        super.initCreateView()

        mDataBinding.ivQuitRecharge.setOnClickListener {
            destroyDialog()
        }
        mDataBinding.tvFilter.setOnClickListener {
            val area = mDataBinding.etFilterContent.text.trim().toString()
            lifecycleScope.launch {
                val requestData = when(type){
                    AppointType.YiMiao -> {
                        fastRequest<List<AppointChooseAreaBean>> {
                            SystemRepository.getYiMiaoArea(area)
                        }
                    }
                    AppointType.HeSuan -> {
                        fastRequest<List<AppointChooseAreaBean>> {
                            SystemRepository.getHeSuanArea(area)
                        }
                    }
                    else-> null
                }
                requestData?.let {
                    if (it.isEmpty()) {
                        ToastUtils.toastShort("当前城市未查询到可用地点")
                        return@launch
                    }
                    list.clear()
                    it.forEach {
                        list.add(it.site)
                    }
                    val adapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        list
                    )
                    mDataBinding.listView.adapter = adapter
                }

            }
        }
        mDataBinding.listView.setOnItemClickListener { adapterView, view, position, id ->
            val area = list[position]
            block(area)
            destroyDialog()
        }
    }

    companion object {
        enum class AppointType {
            YiMiao, HeSuan
        }
    }
}