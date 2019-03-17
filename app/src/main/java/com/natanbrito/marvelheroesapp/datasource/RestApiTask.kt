package com.natanbrito.marvelheroesapp.datasource

import com.natanbrito.marvelheroesapp.BuildConfig
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class RestApiTask {

    private var okHttpClient: OkHttpClient = OkHttpClient()
    private lateinit var retrofit: Retrofit


    fun configure(): MarvelApi {
        retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val marvelApi = retrofit.create(MarvelApi::class.java)

        return marvelApi
    }


    fun getOkHttpClient(): OkHttpClient {
        if (okHttpClient != null)
            return okHttpClient

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                return chain.proceed(request)
            }
        })

        clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        clientBuilder.connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)

        okHttpClient = clientBuilder.build()

        return okHttpClient
    }
}