package com.natanbrito.marvelheroesapp.datasource.api

import com.natanbrito.marvelheroesapp.model.Info
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")

    fun charactersList(
        @Query("ts") ts: String,
        @Query("apikey") key: String,
        @Query("hash") hash: String
    ): Observable<Info>

}