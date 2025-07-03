package com.base.library.components

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.base.library.dialog.LoadingDialog
import com.base.library.utils.ViewBindingUtil

open class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (_binding == null) {
            _binding =
                ViewBindingUtil.inflateWithGeneric<VB>(this, inflater, container, false).apply {
                    if (root.background == null) root.setBackgroundColor(Color.WHITE)
                }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadingDialog()
        extractArguments()
        initView()
        initData()
    }

    private fun initLoadingDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    open fun extractArguments() {}

    open fun initView() {}

    open fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}