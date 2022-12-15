package com.example.mvppetproject.homeMVP.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.mvppetproject.R
import com.example.mvppetproject.api.Api
import com.example.mvppetproject.base.BaseFragment
import com.example.mvppetproject.homeMVP.contract.HomeContract
import com.example.mvppetproject.homeMVP.presenter.HomePresenter
import com.example.mvppetproject.model.CoverData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(), HomeContract.View {

    private var presenter: HomeContract.Presenter? = null

    @Inject
    lateinit var repository: Api

    private val _cardList = MutableSharedFlow<List<CoverData>>(
        replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND
    )
    val cardList: SharedFlow<List<CoverData>> = _cardList.asSharedFlow()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        presenter = HomePresenter(this@HomeFragment, repository)
        setContent {
            HomeScreen(cardList)
        }
    }

    override fun showCovers(covers: List<CoverData>) {
        _cardList.tryEmit(covers)
    }

    override fun getScope() = lifecycleScope

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}

@Composable
private fun HomeScreen(itemsFlow: SharedFlow<List<CoverData>>) {
    var listItems by remember { mutableStateOf(listOf<CoverData>()) }

    val p = listOf(
        CoverData.getEmpty,
        CoverData.getEmpty,
        CoverData.getEmpty,
    )

    LaunchedEffect(key1 = Unit) { itemsFlow.collect { listItems = it } }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                top = 22.dp, start = 18.dp, end = 18.dp, bottom = 22.dp
            )
        ) {
            items(listItems) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(it.image)
                        .crossfade(true).size(Size.ORIGINAL).build(),
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

@Preview(widthDp = 411, heightDp = 731, showBackground = true)
@Composable
private fun Preview_HomeScreen() {

    val _cardList = MutableStateFlow(
        listOf(
            CoverData.getEmpty,
            CoverData.getEmpty,
            CoverData.getEmpty,
        )
    )
    val cardList: SharedFlow<List<CoverData>> = _cardList.asSharedFlow()

    HomeScreen(cardList)
    _cardList.tryEmit(
        listOf(
            CoverData.getEmpty,
            CoverData.getEmpty,
            CoverData.getEmpty,
        )
    )
}