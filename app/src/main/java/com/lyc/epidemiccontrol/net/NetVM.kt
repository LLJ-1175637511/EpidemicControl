package com.lyc.epidemiccontrol.net

import androidx.lifecycle.ViewModel
import com.lyc.epidemiccontrol.ext.NET_DATA_TAG
import com.lyc.epidemiccontrol.ext.NET_EXC_TAG
import com.lyc.epidemiccontrol.ext.baseConverter
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.utils.LogUtils
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class NetVM :ViewModel(){

    suspend inline fun <reified T> fastRequest(
        crossinline block: suspend () -> BaseBean
    ):T? = withContext(coroutineContext){
        var data: T? = null
        runCatching {
            val baseBean = block()
            LogUtils.d(NET_DATA_TAG, baseBean.toString())
            if (baseBean.code.isCodeSuc()) {
                data = baseConverter<T>(baseBean)
            }else {
                throw Exception("请求错误 --> errCode:${baseBean.code} errMsg:${baseBean.message}")
            }
        }.onFailure {
            val exc = Exception(it)
            LogUtils.d(NET_EXC_TAG, "网络错误:${exc.message}")
            ToastUtils.toastShort(exc.message.toString())
        }
        data
    }
}