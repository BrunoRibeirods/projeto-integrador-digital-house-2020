package com.example.filmly.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.SearchListsAdapter
import com.example.filmly.domain.HeadLists
import com.example.filmly.domain.getActors
import com.example.filmly.domain.getFilms
import com.example.filmly.domain.getSeries
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.rv_searchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchListsAdapter(getResultsLists(), SearchListsAdapter.SeeMoreNavigation { headLists ->
                val action = SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
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