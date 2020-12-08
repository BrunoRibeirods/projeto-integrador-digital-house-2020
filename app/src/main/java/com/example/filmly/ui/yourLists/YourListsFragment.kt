package com.example.filmly.ui.yourLists

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
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.HeadLists
import kotlinx.android.synthetic.main.fragment_your_lists.view.*

class YourListsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_your_lists, container, false)

        val lista = mutableListOf<Card>()

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        view.rv_yourLists.apply {
            adapter = YourListsAdapter(getContent(lista, "Filme"), YourListsAdapter.SeeMoreNavigation { headLists ->
                val action =
                    YourListsFragmentDirections.actionYourListsFragmentToFavoriteListsFragment(
                        headLists
                    )
                findNavController().navigate(action)
            })
            layoutManager = LinearLayoutManager(context)

        }

        return view
    }

    fun getContent(list: List<Card>, tipo:String): List<HeadLists>{
        val heads = mutableListOf<HeadLists>()
        heads.add(HeadLists("Resultado para $tipo", list))

        return heads
    }

}