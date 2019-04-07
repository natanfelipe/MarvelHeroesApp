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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private var utils = Utils()

    private var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)

    lateinit var charactersAdapter: CharactersAdapter

     val charactersViewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private var charactersList = MutableLiveData<List<Characters>>()

    private val ts = utils.getTimeStamp()
    private val hash = utils.getHash(ts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

        charactersViewModel.charactersList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                characters -> charactersAdapter.submitList(characters)
                isLoaded(true)
            },{isLoaded(false)})

        charactersAdapter.notifyDataSetChanged()
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

