package com.example.filmly.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.SearchListsAdapter
import com.example.filmly.data.model.HeadLists
import com.example.filmly.data.model.getActors
import com.example.filmly.data.model.getFilms
import com.example.filmly.data.model.getSeries
import com.example.filmly.network.TmdbApiteste
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {
    private val serviceApi = TmdbApiteste

    val viewModel by viewModels<SearchViewModel>(){
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(serviceApi.retrofitService) as T
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)


        view.rv_searchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchListsAdapter(getResultsLists(), SearchListsAdapter.SeeMoreNavigation { headLists ->
                val action =
                    SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
                findNavController().navigate(action)
            })

        }

        return view
    }

    fun getResultsLists(): List<HeadLists> {
        val result01 = HeadLists("Resultados para filmes", getFilms())
        val result02 = HeadLists("Resultados para séries", getSeries())
        val result03 = HeadLists("Resultados para atores", getActors())
        return listOf(result03, result01, result02)
    }
}