package com.example.filmly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.domain.HeadLists
import com.example.filmly.domain.ViewMoreCard
import kotlinx.android.synthetic.main.fragment_favorite_lists.*
import kotlinx.android.synthetic.main.fragment_favorite_lists.view.*


class FavoriteListsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_favorite_lists, container, false)

        val rc_favorite = view.findViewById<RecyclerView>(R.id.rc_favorite_lists)

        val args = arguments?.getSerializable("headList") as HeadLists

        view.toolbar_favoriteLists.title = args.titleMessage

        view.toolbar_favoriteLists.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        rc_favorite.adapter = ViewMoreAdapter(args.data, ViewMoreAdapter.CardDetailNavigation{
            val action = FavoriteListsFragmentDirections.actionFavoriteListsFragmentToCardDetailFragment(it)
            findNavController().navigate(action)
        })
        rc_favorite.layoutManager = GridLayoutManager(context, 2)
        rc_favorite.setHasFixedSize(true)


        return view
    }

}