package com.example.filmly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.domain.Actor
import com.example.filmly.domain.Film
import com.example.filmly.domain.Serie
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val filmsRecyclerView = view.rv_topFilmsToday
        val seriesRecyclerView = view.rv_topSeriesToday
        val actorsRecyclerView = view.rv_topActorsToday

        filmsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TopFilmsAdapter(getFilms())
            setHasFixedSize(true)
        }

        seriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TopSeriesAdapter(getSeries())
            setHasFixedSize(true)
        }

        actorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TopActorsAdapter(getActors())
            setHasFixedSize(true)
        }

        return view
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