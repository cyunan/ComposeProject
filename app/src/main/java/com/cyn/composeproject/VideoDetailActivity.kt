package com.cyn.composeproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.cyn.composeproject.ext.toPx

class VideoDetailActivity : ComponentActivity() {
    companion object{
        private const val VIDEO_URL = "video_url"
        private const val VIDEO_TITLE = "video_title"
        fun go(context: Activity, url: String, title: String){
            Intent(context, VideoDetailActivity::class.java).also {
                it.putExtra(VIDEO_URL, url)
                it.putExtra(VIDEO_TITLE, title)
                context.startActivity(it)
                context.overridePendingTransition(R.anim.slide_bottom_in, 0)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(VIDEO_URL)!!
        val title = intent.getStringExtra(VIDEO_TITLE)!!
        setContent {
            VideoViewPage(url = url, title = title) {
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_bottom_out, 0)
    }

    override fun onBackPressed() {
        //视频暂停
        if(Jzvd.backPress()){
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}
@Composable
fun VideoViewPage(url: String, title: String, click: ()->Unit) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                val jzvdStd = JzvdStd(context)
                jzvdStd.layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200.toPx())
                jzvdStd
            },
            modifier = Modifier.align(Alignment.Center)
        ){
            it.setUp(url, title)
            it.startVideoAfterPreloading()
        }
        BackIcon(click =click)
    }
}

@Composable
fun BackIcon(click: () -> Unit) {
    Surface(shape = CircleShape,
        modifier = Modifier.padding(15.dp,35.dp,0.dp,0.dp).size(24.dp)
        ,color = Color.Gray) {
        Icon(
            Icons.Default.KeyboardArrowDown,contentDescription = ""
            ,tint = Color.Gray,modifier = Modifier.clickable {
                click()
            })
    }
}

