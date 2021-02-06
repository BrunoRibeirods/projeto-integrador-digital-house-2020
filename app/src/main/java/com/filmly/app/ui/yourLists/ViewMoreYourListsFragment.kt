package com.filmly.app.ui.yourLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.filmly.app.R
import com.filmly.app.adapters.ViewMoreAdapter
import com.filmly.app.data.model.HeadLists
import com.filmly.app.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.fragment_view_more_your_lists.view.*


class ViewMoreYourListsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_more_your_lists, container, false)

        val rc_favorite = view.findViewById<RecyclerView>(R.id.rc_favorite_lists)

        val args = arguments?.getSerializable("headList") as HeadLists

        view.toolbar_favoriteLists.title = args.titleMessage

        view.toolbar_favoriteLists.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        rc_favorite.adapter = ViewMoreAdapter(args.cardInfo, CardDetailNavigation{
            val action =
                ViewMoreYourListsFragmentDirections.actionFavoriteListsFragmentToCardDetailFragment(
                    it
                )
            findNavController().navigate(action)
        }).also { it.submitList(args.data) }
        rc_favorite.layoutManager = GridLayoutManager(context, 2)
        rc_favorite.setHasFixedSize(true)


        return view
    }

}