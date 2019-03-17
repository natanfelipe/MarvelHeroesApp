package com.natanbrito.marvelheroesapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.natanbrito.marvelheroesapp.model.Characters
import com.natanbrito.marvelheroesapp.repository.CharactersRepository
import org.jetbrains.annotations.NotNull

class CharactersViewModel (@NotNull application: Application) : AndroidViewModel(application) {

    fun getAllCharacters(context: Context, ts: String, apiKey: String, hash: String
                         , charactersList: MutableLiveData<List<Characters>>){

        val repository = CharactersRepository()
        repository.getAllCharacters(context,ts,apiKey,hash,charactersList)
    }
}

