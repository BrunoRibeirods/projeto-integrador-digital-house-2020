package com.example.filmly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_card_detail.view.*

class CardDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_detail, container, false)

        val id = arguments?.getInt("id")
        view.tv_cardId.text = "Make a Retrofit Request with this ID: $id"

        return view
    }
}