package com.example.mvppetproject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.mvppetproject.homeMVVM.HomeState
import com.example.mvppetproject.model.CoverData


@Composable
fun HomeScreen(state: HomeState) {
    when (state) {
        is HomeState.Loading -> {
            ProgressScreen()
        }
        is HomeState.DataLoaded -> {
            ListDataScreen(listItems = state.covers)
        }
        is HomeState.Error -> {
            LoadingErrorScreen(error = state.error)
        }
    }
}

@Composable
private fun ListDataScreen(listItems: List<CoverData>) {
    Column {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                top = 22.dp, start = 18.dp, end = 18.dp, bottom = 22.dp
            )
        ) {
            items(listItems, key = { it.textId }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(it.image)
                        .diskCacheKey(it.image).memoryCacheKey(it.image).crossfade(true)
                        .size(Size.ORIGINAL).build(),
                    contentDescription = "Photo",
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )
                Text(
                    text = it.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = it.description ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 12.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ProgressScreen() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadingErrorScreen(error: Throwable) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Text(text = error.toString())
    }
}

@Preview(widthDp = 411, heightDp = 731, showBackground = true)
@Composable
private fun Preview_HomeScreen() {
    val previewList = listOf(
        CoverData.getEmpty,
        CoverData.getEmpty,
        CoverData.getEmpty,
    )

    ListDataScreen(previewList)
}

@Preview(widthDp = 411, heightDp = 731, showBackground = true)
@Composable
private fun Preview_ProgressScreen() {
    ProgressScreen()
}

@Preview(widthDp = 411, heightDp = 731, showBackground = true)
@Composable
private fun Preview_LoadingErrorScreen() {
    LoadingErrorScreen(
        Throwable()
    )
}