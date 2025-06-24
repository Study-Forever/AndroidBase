package com.base.library.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.core.graphics.drawable.toDrawable
import com.base.library.R
import com.base.library.databinding.DialogLoadingBinding
import com.base.library.utils.gone

class LoadingDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
    private lateinit var binding: DialogLoadingBinding
    var loadingMessage: String? = null

    init {
        // Remove the default dialog title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        window?.setDimAmount(0.6f) // Background dimming

        binding.apply {
            loadingMessage?.let { tvLoadingMessage.text = it } ?: tvLoadingMessage.gone()
        }
    }
}
