package com.natanbrito.marvelheroesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.natanbrito.marvelheroesapp.datasource.RestApiTask
import com.natanbrito.marvelheroesapp.datasource.api.MarvelApi
import com.natanbrito.marvelheroesapp.extensions.md5
import com.natanbrito.marvelheroesapp.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var marvelApi: MarvelApi
    private val restApiTask = RestApiTask()
    private var utils = Utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        marvelApi = restApiTask.configure()
        val ts = utils.getTimeStamp()

        compositeDisposable.addAll(
            marvelApi.charactersList(ts, BuildConfig.PUBLIC_API_KEY,
                "$ts${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ heroesResponse ->
                    Log.d("TESTE", "size " + heroesResponse.data.results.size)
                }, { throwable -> Log.d("TESTE", "error ${throwable.localizedMessage}") })
        )
    }
}
