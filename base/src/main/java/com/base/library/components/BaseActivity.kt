package com.base.library.components

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.base.library.dialog.LoadingDialog
import com.base.library.utils.ViewBindingUtil

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    lateinit var loadingDialog: LoadingDialog

    override fun onStart() {
        super.onStart()
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        _binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(_binding!!.root)
        initLoadingDialog()
        extractIntent()
        initView()
        initData()
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog(this)
    }

    open fun extractIntent() {}
    open fun initView() {}
    open fun initData() {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}