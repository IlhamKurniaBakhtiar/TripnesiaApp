package com.tripnesia.mobile.ui.payment

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PaymentScreen(
    snapUrl: String,
    onPaymentFinished: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        url?.let {
                            if (it.contains("finish")) {
                                onPaymentFinished()
                            }
                        }
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        return false // biar semua tetap dibuka di WebView, bukan browser
                    }
                }
                Log.d("SnapDebug", "Opening Snap URL: $snapUrl")
                webChromeClient = WebChromeClient()

                loadUrl(snapUrl)
            }
        },

        update = { webView ->
            webView.loadUrl(snapUrl) // pastikan reload kalau SnapToken baru
        }
    )
}

