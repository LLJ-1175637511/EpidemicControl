package com.lyc.epidemiccontrol.net

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.ext.*
import com.lyc.epidemiccontrol.ui.fragment.BaseFragment
import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib
import com.lyc.epidemiccontrol.utils.LogUtils
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.withContext

abstract class NetFragment<DB : ViewDataBinding>:BaseFragment<DB>() {

    suspend inline fun <reified T> fastRequest(
        isLogin:Boolean = false,
        crossinline block:suspend () -> BaseBean
    ):T? = withContext(this.lifecycleScope.coroutineContext){
        var data: T? = null
        runCatching {
            val baseBean = block()
            LogUtils.d(NET_DATA_TAG, baseBean.toString())
            if (baseBean.code.isCodeSuc()) {
                data = baseConverter<T>(baseBean)
                if (isLogin){
                    ECLib.getSP(Const.SPNet).save {
                        putString(Const.SPNetToken,baseBean.message)
                    }
                }
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