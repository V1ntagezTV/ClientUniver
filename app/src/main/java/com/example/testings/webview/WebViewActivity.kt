package com.example.testings.webview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.wbaction_openInBrowser -> {
                openNewTabWindow(webView.url)
            }
            R.id.wbaction_request -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, webView.url)
                try {
                    startActivity(Intent.createChooser(intent, "Поделиться ссылкой"))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(applicationContext, "Ошибка отправки", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }
}