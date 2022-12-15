package com.example.mvppetproject.homeMVP.contract

import com.example.mvppetproject.model.CoverData
import kotlinx.coroutines.CoroutineScope

interface HomeContract {
    interface View {
        fun showCovers(covers: List<CoverData>)
        fun getScope(): CoroutineScope
    }

    interface Presenter {
        fun onDestroy()
    }

}