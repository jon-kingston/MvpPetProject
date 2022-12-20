package com.example.mvppetproject.homeMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvppetproject.api.Api
import com.example.mvppetproject.helpers.log
import com.example.mvppetproject.model.CoverData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            delay(400)
            runCatching { api.getCovers() }
                .onSuccess { _state.emit(HomeState.DataLoaded(it)) }
                .onFailure { _state.emit(HomeState.Error(it)) }
        }
    }
}