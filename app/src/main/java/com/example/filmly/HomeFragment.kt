package com.example.filmly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.domain.Actor
import com.example.filmly.domain.Film
import com.example.filmly.domain.Serie
import com.example.filmly.domain.Trending
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.rv_homeLists
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeListsAdapter(getListTrending(), SeeMoreNavigation { trending ->
                val action = HomeFragmentDirections.actionHomeFragmentToTrendingList(trending)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }
        return view
    }

    fun getListTrending(): List<Trending> {
        val genre01 = Trending("Top Filmes de Hoje", getFilms())
        val genre02 = Trending("Top Séries de Hoje", getSeries())
        val genre03 = Trending("Top Atores de Hoje", getActors())
        val genre04 = Trending("Misturado", getSeries() + getFilms() + getActors())
        return listOf(genre01, genre02, genre03, genre04)
    }

    fun getFilms(): List<Film> {
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