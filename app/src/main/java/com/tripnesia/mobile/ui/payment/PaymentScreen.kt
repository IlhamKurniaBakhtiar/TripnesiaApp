package com.tripnesia.mobile.ui.payment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

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

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        Log.d("SnapDebug", "Started: $url")

                        val isSuccessRedirect = url?.run {
                            contains("status_code=200") || contains("transaction_status=settlement")
                        } ?: false

                        if (isSuccessRedirect) {
                            view?.stopLoading()
                            onPaymentFinished()
                        }

                        //  Cegah HTTP redirect yang error (http://example.com)
                        if (url?.startsWith("http://") == true) {
                            view?.stopLoading()
                        }
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        val url = request?.url.toString()
                        Log.d("SnapDebug", "Redirect URL: $url")

                        if (url.contains("status_code=200") || url.contains("transaction_status=settlement")) {
                            view?.stopLoading()
                            onPaymentFinished()
                            return true
                        }

                        if (url.startsWith("http://")) {
                            // Blokir semua HTTP non-HTTPS
                            return true
                        }

                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("SnapDebug", "Finished loading: $url")
                    }
                }

                webChromeClient = WebChromeClient()
                loadUrl(snapUrl)
            }
        },
        update = { webView ->
            webView.loadUrl(snapUrl)
        }
    )
}
