package com.natanbrito.marvelheroesapp.datasource.paging

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.model.Characters
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
    private val marvelApi: MarvelApi,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Characters>() {

    override fun create(): DataSource<Int, Characters> {
        return CharactersDataSource(marvelApi,compositeDisposable)
    }
}