package com.example.filmly.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.HomeListsAdapter
import com.example.filmly.domain.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        val recyclerView = view.rv_homeLists
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeListsAdapter(getListTrending(), HomeListsAdapter.SeeMoreNavigation { trending ->
                val action = HomeFragmentDirections.actionHomeFragmentToTrendingList(trending)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }
        return view
    }

    fun getListTrending(): List<HeadLists> {
        val genre01 = HeadLists("Top Filmes de Hoje", getFilms())
        val genre02 = HeadLists("Top Séries de Hoje", getSeries())
        val genre03 = HeadLists("Top Atores de Hoje", getActors())
        val genre04 = HeadLists("Terror", getHorror() + getHorror())
        val genre05 = HeadLists("Drama", getDrama() + getDrama())
        val genre06 = HeadLists("Comédia", getComedy() + getComedy())
        return listOf(genre01, genre02, genre03, genre04, genre05, genre06)
    }

}