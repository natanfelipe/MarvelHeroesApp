package com.natanbrito.marvelheroesapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natanbrito.marvelheroesapp.BuildConfig
import com.natanbrito.marvelheroesapp.R
import com.natanbrito.marvelheroesapp.model.Characters
import com.natanbrito.marvelheroesapp.ui.adapter.CharactersAdapter
import com.natanbrito.marvelheroesapp.utils.Utils
import com.natanbrito.marvelheroesapp.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private var utils = Utils()

    private var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)

    lateinit var charactersAdapter: CharactersAdapter

    private lateinit var charactersViewModel: CharactersViewModel

    private var charactersList = MutableLiveData<List<Characters>>()

    private val ts = utils.getTimeStamp()
    private val hash = utils.getHash(ts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

        initRecyclerView()

        getAllCharacters()
    }

    fun initRecyclerView() {
        charactersRecyclerView.setHasFixedSize(true)
        charactersRecyclerView.layoutManager = layoutManager
        charactersAdapter = CharactersAdapter()

        charactersRecyclerView.adapter = charactersAdapter
    }

    private fun getAllCharacters() {
        charactersViewModel.getAllCharacters(this, ts, BuildConfig.PUBLIC_API_KEY, hash, charactersList)

        if (utils.hasInternetConnection(this)) {
            charactersList.observe(this, Observer { characters ->
                charactersAdapter.setCharactersList(characters)
                isLoaded(true)
            })

            charactersAdapter.notifyDataSetChanged()
        } else {
            isLoaded(false)
        }
    }

    private fun isLoaded(loaded: Boolean) {
        if (loaded) {
            progressbar.visibility = View.GONE
            errorMessage.visibility = View.GONE
            charactersRecyclerView.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            charactersRecyclerView.visibility = View.GONE

            displayErrorMessage()
        }

    }

    private fun displayErrorMessage() {
        if (utils.hasInternetConnection(this)) {
            errorMessage.text = getString(R.string.lb_sem_personagens)
        } else {
            errorMessage.text = getString(R.string.lb_sem_conexao)
        }
    }
}

