package com.example.mvppetproject.homeMVP

import com.example.mvppetproject.api.Api
import com.example.mvppetproject.api.ApiRepository
import com.example.mvppetproject.helpers.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePresenter(private val mView: HomeContract.View) : HomeContract.Presenter {

    val repository = ApiRepository()

    init {
        mView.showProgress()
        mView.getScope().launch(Dispatchers.IO) {
            log("testPresenter")
            runCatching { repository.getCovers() }
                .onSuccess { mView.showCovers(it) }
                .onFailure { mView.showError(it) }
        }
    }

    override fun onDestroy() {
//        TODO("Not yet implemented")
    }
}