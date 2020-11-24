package com.example.filmly.yourLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.YourListsAdapter
import com.example.filmly.domain.HeadLists
import com.example.filmly.domain.getActors
import com.example.filmly.domain.getFilms
import com.example.filmly.domain.getSeries
import kotlinx.android.synthetic.main.fragment_your_lists.view.*

class YourListsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_your_lists, container, false)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        view.rv_yourLists.apply {
            adapter = YourListsAdapter(getLists(), YourListsAdapter.SeeMoreNavigation { headLists ->
                val action = YourListsFragmentDirections.actionYourListsFragmentToListsFragment(headLists)
                findNavController().navigate(action)
            })
            layoutManager = LinearLayoutManager(context)

        }

        return view
    }

    fun getLists(): List<HeadLists> {
        val list01 = HeadLists("Séries Favoritas", getSeries())
        val list02 = HeadLists("Meus Favoritos", getFilms() + getSeries() + getActors())
        val list03 = HeadLists("Atores Favoritos", getActors())
        return listOf(list02, list01, list03)
    }
}