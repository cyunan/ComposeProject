package com.cyn.composeproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.cyn.composeproject.ui.theme.ComposeProjectTheme
import com.cyn.composeproject.view.CommonTitleBar
import com.cyn.composeproject.view.ProgressWebView

class NewsDetailActivity : ComponentActivity() {

    private lateinit var mWebView: ProgressWebView

    companion object{
        const val TITLE = "title"
        const val CONTENT = "content"

        fun go(context: Context, title: String, content: String){
            Intent(context, NewsDetailActivity::class.java).also {
                it.putExtra(TITLE, title)
                it.putExtra(CONTENT, content)
                context.startActivity(it)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra(TITLE)
        val content = intent.getStringExtra(CONTENT)
        mWebView = ProgressWebView(this).apply {
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            webViewClient = WebViewClient()
        }
        setContent {
            ComposeProjectTheme {
                if (title == null || content == null) return@ComposeProjectTheme
                NewsDetailPage(
                    title = title,
                    content = content,
                    backClick = {
                        if (mWebView.canGoBack()){
                            mWebView.goBack()
                        }else{
                            finish()
                        }
                    },
                    webView = mWebView
                )

            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}



@Composable
fun NewsDetailPage(title: String, content:String, backClick: ()-> Unit, webView: WebView) {
    Column {
        CommonTitleBar(text = title, showBackIcon = true, backClick = backClick)
        AndroidView(factory = { webView }){
            it.loadUrl(content)
        }
    }
}