package com.example.mvppetproject.homeMVVM

import com.example.mvppetproject.model.CoverData

sealed class HomeState {
    object Loading : HomeState()
    class DataLoaded( val covers: List<CoverData> ): HomeState()
    class Error(val error: Throwable): HomeState()
}
