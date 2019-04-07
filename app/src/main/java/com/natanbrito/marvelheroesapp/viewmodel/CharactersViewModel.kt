package com.natanbrito.marvelheroesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.natanbrito.marvelheroesapp.datasource.RestApiTask
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.datasource.paging.CharactersDataSourceFactory
import com.natanbrito.marvelheroesapp.model.Characters
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    var charactersList: Observable<PagedList<Characters>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private val sourceFactroy: CharactersDataSourceFactory
    private var marvelApi: MarvelApi

    init {
        val restApiTask = RestApiTask()
        marvelApi = restApiTask.configure()
        sourceFactroy = CharactersDataSourceFactory(marvelApi, compositeDisposable)
        val config = PagedList.Config.Builder().setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize*2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        charactersList = RxPagedListBuilder(sourceFactroy,config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

