package com.example.mvppetproject.homeMVP

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
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
import com.example.mvppetproject.HomeScreen
import com.example.mvppetproject.R
import com.example.mvppetproject.api.Api
import com.example.mvppetproject.base.BaseFragment
import com.example.mvppetproject.homeMVP.HomeContract
import com.example.mvppetproject.homeMVP.HomePresenter
import com.example.mvppetproject.homeMVVM.HomeState
import com.example.mvppetproject.model.CoverData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(), HomeContract.View {

    private var presenter: HomeContract.Presenter? = null

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        presenter = HomePresenter(this@HomeFragment)
        setContent {
            HomeScreen(state.collectAsState().value)
        }
    }

    override fun showCovers(covers: List<CoverData>) {
        _state.tryEmit(HomeState.DataLoaded(covers))
    }

    override fun showProgress() {
        _state.tryEmit(HomeState.Loading)
    }

    override fun showError(error: Throwable) {
        _state.tryEmit(HomeState.Error(error))
    }

    override fun getScope() = lifecycleScope

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}