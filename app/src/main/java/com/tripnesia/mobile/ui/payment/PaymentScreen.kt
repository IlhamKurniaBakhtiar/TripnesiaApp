package com.tripnesia.mobile.ui.payment

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.Composable

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MidtransWebViewScreen(
    snapUrl: String,
    onPaymentFinished: () -> Unit
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    if (url?.contains("finish") == true) {
                        onPaymentFinished()
                    }
                }
            }
            loadUrl(snapUrl)
        }
    })
}
