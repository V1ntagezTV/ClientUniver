package com.example.testings.webview

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.testings.R


class WebViewActivity: AppCompatActivity() {

    var link: String? = null
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)
        setSupportActionBar(findViewById(R.id.webview_toolbar))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_exit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        link = intent.getStringExtra("link")

        webView = findViewById(R.id.testing)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                supportActionBar?.title = view?.title
                supportActionBar?.subtitle = url
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                setTitle("Loading...")
                if (newProgress == 100) {
                    setTitle(R.string.app_name)
                }
            }
        }
        webView.loadUrl(link)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}