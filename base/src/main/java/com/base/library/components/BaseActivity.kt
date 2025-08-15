package com.base.library.components

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.base.library.dialog.LoadingDialog
import com.base.library.utils.ViewBindingUtil
import com.base.library.utils.safeDismiss
import com.base.library.utils.safeShow

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)

        // 设置透明状态栏和导航栏
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // 让内容延伸到系统栏后面
        WindowCompat.setDecorFitsSystemWindows(window, false)

        _binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)

        // 设置浅色状态栏和导航栏图标
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        // 处理系统栏插入
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            // 应用内边距，防止内容被系统栏遮挡
            view.updatePadding(
                left = insets.left,
                top = 0, //top设置为0可以实现沉浸式状态栏，配置marginTop:?actionBarSize使用
                right = insets.right,
                bottom = insets.bottom
            )

            // 返回消耗的insets
            WindowInsetsCompat.CONSUMED
        }
        initLoadingDialog()
        extractIntent()
        initView()
        initData()
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog(this)
    }

    fun showLoadingDialog() {
        loadingDialog.safeShow()
    }

    fun dismissLoadingDialog() {
        loadingDialog.safeDismiss()
    }

    open fun extractIntent() {}
    open fun initView() {}
    open fun initData() {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}