package com.cyn.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.blankj.utilcode.util.BarUtils
import com.cyn.composeproject.ui.page.MainTabPage
import com.cyn.composeproject.ui.theme.ComposeProjectTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            val navController = rememberAnimatedNavController()
            // 思考一个问题 fragment activity
            //1、在fragment 当中我们去实例化 我们的ComposeView
            //2、navigation-compose
            ComposeProjectTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(Color.Transparent,darkIcons = false)
                }
                ProvideWindowInsets {
                    MainTabPage(navController)
                }

            }
        }
    }


}
