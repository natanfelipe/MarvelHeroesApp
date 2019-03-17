package com.natanbrito.marvelheroesapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.natanbrito.marvelheroesapp.model.Characters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.natanbrito.marvelheroesapp.datasource.RestApiTask
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.utils.Utils


class CharactersRepository {

    private var compositeDisposable = CompositeDisposable()
    var marvelApi: MarvelApi
    private var utils = Utils()

    init {
        var restApiTask = RestApiTask()
        marvelApi =  restApiTask.configure()
    }

    fun getAllCharacters(context: Context,ts: String, apiKey: String, hash: String,
                         charactersList: MutableLiveData<List<Characters>>){

        if (utils.hasInternetConnection(context)){
            compositeDisposable.addAll(
                marvelApi.charactersList(ts, apiKey,
                    hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ heroesResponse ->
                        charactersList.value = heroesResponse.data.results
                    }, { throwable -> Log.d("TESTE", "error ${throwable.localizedMessage}") })
            )
        }

    }
}
