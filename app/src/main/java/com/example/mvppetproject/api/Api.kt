package com.example.mvppetproject.api

import com.example.mvppetproject.model.CoverData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("covers/")
    suspend fun getCovers(): List<CoverData>

}