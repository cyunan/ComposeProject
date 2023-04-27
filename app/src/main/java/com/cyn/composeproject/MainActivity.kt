package com.cyn.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.cyn.composeproject.ui.page.MainTabPage
import com.cyn.composeproject.ui.theme.ComposeProjectTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
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
                ProvideWindowInsets {
                    val systemUiController = rememberSystemUiController()
                    SideEffect {
                        systemUiController.setStatusBarColor(Color.Transparent,darkIcons = false)
                    }

                    //配置路由
                    AnimatedNavHost(navController = navController,
                        modifier = Modifier.fillMaxWidth(),
                        startDestination = "mainTab",
                        enterTransition = {
                                _, _ ->
                            slideInHorizontally(
                                initialOffsetX = {it},
                                animationSpec = tween()
                            )
                        },
                        exitTransition = {
                                _, _ ->
                            slideOutHorizontally(
                                targetOffsetX = {it},
                                animationSpec = tween()
                            )
                        },
                        popEnterTransition = {
                                _, _ ->
                            slideInHorizontally(
                                initialOffsetX = {-it},
                                animationSpec = tween()
                            )
                        },
                        popExitTransition = {
                                _, _ ->
                            slideOutHorizontally(
                                targetOffsetX = {it},
                                animationSpec = tween()
                            )
                        }){
                        composable(
                            route = "splash",exitTransition = {_,_ -> fadeOut()}
                        ){}
                        composable(
                            route = "setting",exitTransition = {_,_ -> fadeOut()}
                        ){}

                        composable(
                            route = "mainTab",exitTransition = {_,_ -> fadeOut()}
                        ){
//                            MainTabPage(navController)
                        }
                    }
                }
            }
        }
    }


}
