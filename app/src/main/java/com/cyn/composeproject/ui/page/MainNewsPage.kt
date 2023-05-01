package com.cyn.composeproject.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.transform.RoundedCornersTransformation
import com.cyn.composeproject.NewsDetailActivity
import com.cyn.composeproject.R
import com.cyn.composeproject.ext.toPx
import com.cyn.composeproject.model.NewsModelModel
import com.cyn.composeproject.model.StoryModel
import com.cyn.composeproject.model.TopStoryModel
import com.cyn.composeproject.view.CommonTitleBar
import com.cyn.composeproject.view.LoadingPage
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.cyn.composeproject.viewmodel.MainNewsViewModel

/**
 * 首页
 */
@Composable
fun MainNewsPage(context: String) {
    val viewModel: MainNewsViewModel = viewModel()
    val state by viewModel.stateLiveData.observeAsState()
    val newsModel by viewModel.newsLiveData.observeAsState(NewsModelModel())
    LoadingPage(
        state = state!!,
        loadInit = {viewModel.getNewsLists()}
    ) {
        Column(Modifier.fillMaxSize()) {
            CommonTitleBar(text = stringResource(id = R.string.news))
            LazyColumn {
                val stories = newsModel.top_stories
                val list = mutableListOf<String>()
                list.add("111")
                list.add("222")
                list.add("333")
                itemsIndexed(stories){ index, item ->
                    Log.e("MainNewsPage","index: $index  item: $item")
//                    Text(text = context)
                    if (index == 0){
                        NewsBanner(newsModel.news)
                    }else{
                        NewsItems(item)
                        if (index != stories.size-1){
                            Divider(
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsBanner(topStories: List<StoryModel>) {
    Box(modifier = Modifier.height(200.dp)){
        val pageState = rememberPagerState(pageCount = topStories.size, infiniteLoop = true)
        HorizontalPager(state = pageState, Modifier.fillMaxSize()) { page ->
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberCoilPainter(
                    request = topStories[page].image,
                    fadeIn = true,
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
        HorizontalPagerIndicator(pagerState = pageState,
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.BottomCenter),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = Color.White
        )
    }

}


@Composable
fun NewsItems(model: TopStoryModel) {
    val context = LocalContext.current
    Row(
        Modifier
            .padding(10.dp)
            .clickable { NewsDetailActivity.go(context, model.title, model.url)}
    ) {
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(80.dp),
            painter = rememberCoilPainter(model.image,
                fadeIn = true,
                requestBuilder = { transformations(RoundedCornersTransformation(2.toPx().toFloat()))}
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        
        Column(Modifier.weight(1f)) {
            Text(text = model.title,
                fontSize = 15.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
            )
            Text(text = model.hint,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
            )

        }
    }
}