package com.example.filmly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmly.domain.HeadLists
import kotlinx.android.synthetic.main.fragment_lists.view.*

class ListsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Replace this fragment layout and logic to show the Card List in
         * a RecyclerView with GridLayoutManager Vertically*/

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lists, container, false)

        //headLists.data has a list of Cards to display
        val trending = arguments?.getSerializable("headLists") as HeadLists

        view.tv_message.text = trending.titleMessage

        return view
    }

}