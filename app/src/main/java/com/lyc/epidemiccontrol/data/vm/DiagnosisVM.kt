package com.lyc.epidemiccontrol.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyc.epidemiccontrol.data.bean.ConfirmedCasesBean
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.net.NetVM
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import kotlinx.coroutines.launch

class DiagnosisVM : NetVM() {

    var casesBean = MutableLiveData<ConfirmedCasesBean>()

    fun getCases(){
        viewModelScope.launch {
            fastRequest<List<ConfirmedCasesBean>> {
                SystemRepository.getCases()
            }?.let {
                val list = it[0] ?: return@let
                casesBean.postValue(list)
            }
        }
    }


}