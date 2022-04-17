package com.lyc.epidemiccontrol.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.ext.NET_DATA_TAG
import com.lyc.epidemiccontrol.ext.NET_EXC_TAG
import com.lyc.epidemiccontrol.ext.baseConverter
import com.lyc.epidemiccontrol.ext.isCodeSuc
import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.utils.LogUtils
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

abstract class BaseDialog<DB : ViewDataBinding> : DialogFragment() {

    abstract fun getLayoutId(): Int

    lateinit var mDataBinding: DB

    open fun initCreate() {}

    open fun initCreateView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.NormalDialog)
        initCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDataBinding = DataBindingUtil.inflate<DB>(inflater, getLayoutId(), container, false)
        mDataBinding.lifecycleOwner = this
        initCreateView()
        return mDataBinding.root
    }

    fun destroyDialog() {
        //隐藏软键盘
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        this.dismiss()
    }

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