package com.example.mvppetproject.homeMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvppetproject.api.Api
import com.example.mvppetproject.helpers.log
import com.example.mvppetproject.model.CoverData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {
    private val _state = MutableSharedFlow<HomeState>(
        replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND
    )
    val state: SharedFlow<HomeState> = _state.asSharedFlow()


    init {
        viewModelScope.launch {
            delay(400)
            kotlin.runCatching { api.getCovers() }
                .onSuccess {
                    _state.tryEmit(HomeState.DataLoaded(it))
                }
                .onFailure { _state.tryEmit(HomeState.Error(it)) }
        }
    }
}