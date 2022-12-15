package com.example.mvppetproject.homeMVP.presenter

import com.example.mvppetproject.api.Api
import com.example.mvppetproject.helpers.log
import com.example.mvppetproject.homeMVP.contract.HomeContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePresenter(
    private val mView: HomeContract.View,
    private val repository: Api
) : HomeContract.Presenter {

    init {
        mView.getScope().launch(Dispatchers.IO) {
            log("testPresenter")
            kotlin.runCatching { repository.getCovers() }
                .onSuccess { mView.showCovers(it) }
                .onFailure { log(it.toString()) }
        }
    }

    override fun onDestroy() {
//        TODO("Not yet implemented")
    }
}