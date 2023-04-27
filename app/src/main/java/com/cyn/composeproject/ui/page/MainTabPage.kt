package com.cyn.composeproject.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cyn.composeproject.ui.theme.Purple500
import com.cyn.composeproject.viewmodel.MainViewModel
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainTabPage(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    val selectedIndex by viewModel.getSelectedIndex().observeAsState(0)
    Column {
        Spacer(
            modifier = Modifier
                .statusBarsHeight()
                .fillMaxWidth()
                .background(Purple500)
        )
        val pageState = rememberPagerState(
            pageCount = 4,
            initialOffscreenLimit = 3,
            initialPage = selectedIndex)

        HorizontalPager(
            state = pageState,
            dragEnabled = false,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> MainPage("新闻")
                1 -> MainPage("视频")
                2 -> MainPage("音乐")
                3 -> MainPage("我的")
            }
        }
        BottomNavigationCompose(pagerState = pageState)
    }

}


val tabList = listOf("新闻", "视频", "音乐", "我的")

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BottomNavigationCompose(pagerState: PagerState) {
    val viewModel: MainViewModel = viewModel()
    val selectIndex by viewModel.getSelectedIndex().observeAsState(0)
    val scope = rememberCoroutineScope()
    BottomNavigation(backgroundColor = Color.White) {
        tabList.forEachIndexed { index, label ->
            BottomNavigationItem(
                label = {
                    Text(
                        text = label,
                        color = if (selectIndex == index) MaterialTheme.colors.primary else Color.Gray
                    )
                },
                selected = selectIndex == index,
                onClick = {
                    viewModel.saveSelectIndex(index)
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                icon = {
                    when(index){
                        0 -> BottomIcon(
                            imageVector = Icons.Filled.Home,
                            selectIndex = selectIndex,
                            index = index
                        )
                        1 -> BottomIcon(
                            imageVector = Icons.Filled.List,
                            selectIndex = selectIndex,
                            index = index
                        )
                        2 -> BottomIcon(
                            imageVector = Icons.Filled.Favorite,
                            selectIndex = selectIndex,
                            index = index
                        )
                        3 -> BottomIcon(
                            imageVector = Icons.Filled.Place,
                            selectIndex = selectIndex,
                            index = index
                        )
                    }
                }
            )

        }
    }
}

@Composable
fun BottomIcon(imageVector: ImageVector, selectIndex: Int, index: Int) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = if (selectIndex == index) MaterialTheme.colors.primary else Color.Gray
    )
}