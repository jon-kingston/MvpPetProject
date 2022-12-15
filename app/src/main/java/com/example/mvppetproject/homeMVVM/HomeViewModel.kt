package com.example.mvppetproject.homeMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvppetproject.api.Api
import com.example.mvppetproject.helpers.log
import com.example.mvppetproject.model.CoverData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {
    private val _cardList = MutableSharedFlow<List<CoverData>>(
        replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND
    )
    val cardList: SharedFlow<List<CoverData>> = _cardList.asSharedFlow()

    init {
        viewModelScope.launch {
            kotlin.runCatching { api.getCovers() }
                .onSuccess { _cardList.tryEmit(it) }
                .onFailure { log("loading ERROR $it") }
        }
    }
}