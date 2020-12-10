package com.example.filmly.ui.home

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
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.StatesRepository
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.tv_greetings.text = getString(R.string.hello_wil, StatesRepository.userInformation.value?.name)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        val recyclerView = view.rv_homeLists

        val lista = mutableListOf<Card>()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeListsAdapter(getContent(lista, "Trending"), HomeListsAdapter.SeeMoreNavigation { trending ->
                val action = HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(trending)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }
        return view
    }


    fun getContent(list: List<Card>, tipo:String): List<HeadLists>{
        val heads = mutableListOf<HeadLists>()
        heads.add(HeadLists("Resultado para $tipo", list))

        return heads
    }



}