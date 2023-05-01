package com.cyn.composeproject.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
fun CommonTitleBar(
    text: String,
    showBackIcon: Boolean = false,
    backClick:(()->Unit)? = null
) {
    TopAppBar(contentPadding = PaddingValues.Absolute()) {
        if (showBackIcon){
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable {
                        backClick?.invoke()
                    },
                contentAlignment = Alignment.CenterStart
            ){
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(15.dp, 0.dp)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}