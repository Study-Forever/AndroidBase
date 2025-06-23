package com.base.library.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.webkit.*
import android.webkit.WebView

class CustomWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var webViewListener: CustomWebViewListener? = null

    init {
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        with(settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(false)
        }

        webViewClient = InternalWebViewClient()
        webChromeClient = InternalWebChromeClient()
    }

    fun setWebViewListener(listener: CustomWebViewListener) {
        this.webViewListener = listener
    }

    // 内部 WebViewClient 实现
    private inner class InternalWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return webViewListener?.shouldOverrideUrlLoading(view, request.url.toString()) ?: run {
                view.loadUrl(request.url.toString())
                true
            }
        }

        override fun onPageStarted(view: WebView, url: String, favicon: android.graphics.Bitmap?) {
            super.onPageStarted(view, url, favicon)
            webViewListener?.onPageStarted(view, url)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            webViewListener?.onPageFinished(view, url)
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            webViewListener?.onReceivedError(
                view,
                error.errorCode,
                error.description.toString(),
                request.url.toString()
            )
        }
    }

    // 内部 WebChromeClient 实现
    private inner class InternalWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            webViewListener?.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let { webViewListener?.onReceivedTitle(view, it) }
        }

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            return webViewListener?.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                ?: super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }
    }

    interface CustomWebViewListener {
        fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean = false
        fun onPageStarted(view: WebView, url: String) {}
        fun onPageFinished(view: WebView, url: String) {}
        fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {}
        fun onProgressChanged(view: WebView, newProgress: Int) {}
        fun onReceivedTitle(view: WebView, title: String) {}
        fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: WebChromeClient.FileChooserParams
        ): Boolean = false
    }
}