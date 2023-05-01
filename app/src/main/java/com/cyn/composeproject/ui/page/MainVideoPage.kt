package com.cyn.composeproject.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

val items = listOf<String>("视频", "笑话", "明星", "股市")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainVideoPage() {
    val tabStr = remember {
        mutableStateOf("视频")
    }
    val state = rememberPagerState(pageCount = items.size)
    val scope = rememberCoroutineScope()
    
    Column {
        TabRow(selectedTabIndex = items.indexOf(tabStr.value),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            indicator = { tabIndicator->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabIndicator[items.indexOf(tabStr.value)])
                )
            },
            divider = {}
        ) {
            items.forEachIndexed{ index, s ->
                val selected = index == items.indexOf(tabStr.value)
                Tab(selected = selected,
                    onClick = {
                        tabStr.value = items[index]
                        scope.launch { state.scrollToPage(index) }
                    }) {
                    Text(text = items[index])
                }
            }
        }

        HorizontalPager(state = state) { page ->
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(page){
                    0 -> ShowMoviePage("111")
                    1 -> ShowMoviePage("222")
                    2 -> ShowMoviePage("333")
                    3 -> ShowMoviePage("444")
                }
            }
            tabStr.value = items[state.currentPage]

        }
    }
}

