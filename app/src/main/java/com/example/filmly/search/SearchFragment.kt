package com.example.filmly.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.HomeListsAdapter
import com.example.filmly.adapters.SearchListsAdapter
import com.example.filmly.domain.Actor
import com.example.filmly.domain.Film
import com.example.filmly.domain.HeadLists
import com.example.filmly.domain.Serie
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        view.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            setIconifiedByDefault(false)
        }

        view.rv_searchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchListsAdapter(getResultsLists(), HomeListsAdapter.SeeMoreNavigation { headLists ->
                val action = SearchFragmentDirections.actionSearchFragmentToListsFragment(headLists)
                findNavController().navigate(action)
            })

        }

        return view
    }

    fun getResultsLists(): List<HeadLists> {
        val result01 = HeadLists("Resultados para filmes", getFilmsResult())
        val result02 = HeadLists("Resultados para séries", getSeries())
        val result03 = HeadLists("Resultados para atores", getActors())
        return listOf(result03, result01, result02)
    }

    fun getFilmsResult(): List<Film> {
        val film01 = Film(1, "Bohemian Rhapsody", R.drawable.bohemian_rhapsody)
        return listOf(film01, film01, film01, film01, film01, film01)
    }

    fun getSeries(): List<Serie> {
        val serie01 = Serie(2, "Mr. Robot", R.drawable.mr_robot)
        return listOf(serie01, serie01, serie01, serie01, serie01, serie01)
    }

    fun getActors(): List<Actor> {
        val actor01 = Actor(3, "Rami Malek", R.drawable.rami_malek)
        return listOf(actor01, actor01, actor01, actor01, actor01, actor01)
    }
}