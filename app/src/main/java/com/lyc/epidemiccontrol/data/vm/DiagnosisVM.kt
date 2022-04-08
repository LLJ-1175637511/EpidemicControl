package com.lyc.epidemiccontrol.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lyc.epidemiccontrol.data.bean.ConfirmedCasesBean
import com.lyc.epidemiccontrol.data.bean.DangerAreaBean
import com.lyc.epidemiccontrol.net.NetVM
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import kotlinx.coroutines.launch

class DiagnosisVM : NetVM() {

    var casesBean = MutableLiveData<ConfirmedCasesBean>()
    var highDangerBean = MutableLiveData<List<DangerAreaBean>>()
    var midDangerBean = MutableLiveData<List<DangerAreaBean>>()

    fun getCases() {
        viewModelScope.launch {
            fastRequest<List<ConfirmedCasesBean>> {
                SystemRepository.getCases()
            }?.let {
                if (it.isEmpty()) return@let
                casesBean.postValue(it[0])
            }
        }
    }

    fun getHighDangerArea() {
        viewModelScope.launch {
            fastRequest<List<DangerAreaBean>> {
                SystemRepository.getHighDangerArea()
            }?.let {
                if (it.isEmpty()) return@let
                highDangerBean.postValue(it)
            }
        }
    }
    fun getMidDangerArea() {
        viewModelScope.launch {
            fastRequest<List<DangerAreaBean>> {
                SystemRepository.getMidDangerArea()
            }?.let {
                if (it.isEmpty()) return@let
                midDangerBean.postValue(it)
            }
        }
    }

    fun filterHighList(content: String): List<DangerAreaBean> = if (content.isEmpty()) {
        getHighDangerArea()
        emptyList()
    } else highDangerBean.value?.filter { it.district.contains(content) } ?: emptyList()

  fun filterMidList(content: String): List<DangerAreaBean> = if (content.isEmpty()) {
        getMidDangerArea()
        emptyList()
    } else midDangerBean.value?.filter { it.district.contains(content) } ?: emptyList()


}