package com.natanbrito.marvelheroesapp.datasource.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.model.Characters
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
    private val marvelApi: MarvelApi,
    private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, Characters>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Characters>) {
        val loadSize = params.requestedLoadSize
        createObservable(0,1,loadSize,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Characters>) {
        val page = params.key
        val loadSize = params.requestedLoadSize
        createObservable(page,page+1,loadSize,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Characters>) {
        val page = params.key
        val loadSize = params.requestedLoadSize
        createObservable(page,page-1,loadSize,null,callback)    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        loadInitialCallback: LoadInitialCallback<Int, Characters>?,
        callback: LoadCallback<Int, Characters>?
    ) {

        compositeDisposable.add(marvelApi.charactersList(requestedPage * requestedLoadSize)
            .subscribe({ response ->
                loadInitialCallback?.onResult(
                    response.data.results, null, adjacentPage
                )
                callback?.onResult(response.data.results, adjacentPage)
                Log.e("ERRO","Erro loading page ${response.data.results}")
            }, {
               Log.e("ERRO","Erro loading page $it.message")
            }
            )
        )
    }
}