package com.cyn.composeproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cyn.composeproject.R
import com.cyn.composeproject.ext.State

@Composable
fun LoadingPage(
    state: State,
    loadInit: (()->Unit)? = null,
    contentView: @Composable BoxScope.()->Unit
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when{
            state.isLoading() -> {
                loadInit?.invoke()
                CircularProgressIndicator()
            }
            state.isError() -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            loadInit?.invoke()
                        }
                ) {
                    Image(
                        painterResource(id = R.mipmap.ic_no_network),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(text = (state as State.Error).errorMessage.toString())
                }
            }
            else -> {
                contentView()
            }
        }
    }
}