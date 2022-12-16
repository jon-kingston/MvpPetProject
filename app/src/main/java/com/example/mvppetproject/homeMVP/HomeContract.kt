package com.example.mvppetproject.homeMVP

import com.example.mvppetproject.model.CoverData
import kotlinx.coroutines.CoroutineScope

interface HomeContract {
    interface View {
        fun showCovers(covers: List<CoverData>)
        fun showProgress()
        fun showError(error: Throwable)
        fun getScope(): CoroutineScope
    }

    interface Presenter {
        fun onDestroy()
    }

}