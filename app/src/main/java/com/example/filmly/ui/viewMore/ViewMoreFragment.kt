package com.example.filmly.ui.viewMore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.adapters.ViewMoreAdapter
import com.example.filmly.data.model.HeadLists
import kotlinx.android.synthetic.main.fragment_view_more.view.*


class ViewMoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_more, container, false)
        val args = arguments?.getSerializable("headList") as HeadLists

        view.toolbar_viewMore.title = args.titleMessage

        view.toolbar_viewMore.setNavigationOnClickListener {
            activity?.onBackPressed()
        }


        val rc_viewMore = view.findViewById<RecyclerView>(R.id.rc_view_more)
        rc_viewMore.adapter = ViewMoreAdapter(args.data, ViewMoreAdapter.CardDetailNavigation {
            val action = ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(it)
            findNavController().navigate(action)
        })
        rc_viewMore.layoutManager = GridLayoutManager(context, 2)
        rc_viewMore.setHasFixedSize(true)

        return view
    }

}