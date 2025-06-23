package com.base.library.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseVmActivity<VB : ViewBinding, VM : ViewModel> : BaseActivity<VB>() {

    protected val viewModel: VM by lazy {
        @Suppress("UNCHECKED_CAST")
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        ViewModelProvider(this)[viewModelClass]
    }
}