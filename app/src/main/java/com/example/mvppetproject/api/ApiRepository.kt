package com.example.mvppetproject.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiRepository {

    suspend fun getCovers() = retrofit.create<Api>().getCovers()

    companion object {
        private val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pivl.github.io/sample_api/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        private fun getOkHttpClient(): OkHttpClient {
            val okHttpBuilder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
            return okHttpBuilder.build()
        }
    }
}
