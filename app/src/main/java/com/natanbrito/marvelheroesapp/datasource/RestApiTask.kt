package com.natanbrito.marvelheroesapp.datasource

import com.natanbrito.marvelheroesapp.BuildConfig
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.utils.Utils
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

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private var utils = Utils()



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
        /*if (okHttpClient != null)
            return okHttpClient*/

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor {
                chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis/1000L).toString()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey",BuildConfig.PUBLIC_API_KEY)
                .addQueryParameter("ts",ts)
                .addQueryParameter("hash",utils.getHash(ts))
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }

        clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        clientBuilder.connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)

        okHttpClient = clientBuilder.build()

        return okHttpClient
    }
}