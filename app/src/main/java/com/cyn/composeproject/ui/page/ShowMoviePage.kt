package com.cyn.composeproject.ui.page

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cyn.composeproject.VideoDetailActivity
import com.cyn.composeproject.ext.formatDateMsByMS
import com.cyn.composeproject.model.MovieItem
import com.cyn.composeproject.model.MovieItemModel
import com.cyn.composeproject.view.LoadingPage
import com.cyn.composeproject.view.StaggeredVerticalGrid
import com.cyn.composeproject.viewmodel.MovieViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ShowMoviePage(text: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        val viewModel: MovieViewModel = viewModel()
        val state by viewModel.stateLiveData.observeAsState()
        val movieList by viewModel.moviesLiveData.observeAsState(listOf())
        LoadingPage(state = state!!,
            loadInit = { viewModel.getMovieLists() }
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                StaggeredVerticalGrid(
                    maxColumnWidth = 220.dp,
                    modifier = Modifier.padding(4.dp)
                ){
                    movieList.forEach { movieItemModel ->
                        MovieItems(movieItemModel)
                    }
                }
            }
        }
    }
    Text(text = text)
}

@Composable
fun MovieItems(model: MovieItemModel) {
    val context = LocalContext.current
    Card(modifier = Modifier.padding(4.dp)) {
        Column(modifier = Modifier.clickable { VideoDetailActivity.go(context as Activity, model.data.playUrl, model.data.title)}) {
            Box {
                Image(painter = rememberCoilPainter(request = model.data.cover.feed, fadeIn = true),
                   contentDescription = null,
                   contentScale = ContentScale.FillHeight,
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(130.dp)
                )
                Text(text = formatDateMsByMS((model.data.duration*1000).toLong()),
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Text(
                text = model.data.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp)
            )
            Text(
                text = model.data.category,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp)
            )
        }

    }
}