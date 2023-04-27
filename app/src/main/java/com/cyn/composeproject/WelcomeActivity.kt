package com.cyn.composeproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blankj.utilcode.util.BarUtils
import com.cyn.composeproject.ui.theme.ComposeProjectTheme
import com.cyn.composeproject.utils.SpUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        if (SpUtils.getBoolean(HAS_SHOW_WELCOME)){
            toMainPage()
        }else{
            setContent{
                ComposeProjectTheme() {
                    ShowWelcomePage(listOf(R.mipmap.image_1, R.mipmap.image_2, R.mipmap.image_3)){
                        SpUtils.setBoolean(HAS_SHOW_WELCOME, true)
                        toMainPage()
                    }
                }
            }
        }
    }

    private fun toMainPage(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ShowWelcomePage(images: List<Int>, toMainPage: ()->Unit) {
        Box(Modifier.fillMaxSize()) {
            val pagerState = rememberPagerState(
                pageCount = images.size,
                initialOffscreenLimit = 2
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            //判断当前页是不是最后一页
            if (pagerState.currentPage == images.size -1){
                Button(
                    onClick = { toMainPage() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
                ) {
                    Text(
                        stringResource(id = R.string.start_experience),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                activeColor = MaterialTheme.colors.primary,
                inactiveColor = Color.White
            )
        }
    }

    companion object{
        const val HAS_SHOW_WELCOME = ""
    }
}

