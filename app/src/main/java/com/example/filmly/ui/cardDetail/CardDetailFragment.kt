package com.example.filmly.ui.cardDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmly.R
import com.example.filmly.data.model.CardDetail
import kotlinx.android.synthetic.main.fragment_card_detail.view.*

class CardDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_detail, container, false)

        val detail = arguments?.getSerializable("detail") as CardDetail

        view.tv_titleDetail.text = detail.title
        view.iv_cardDetailImage.setImageResource(detail.cardImage)
        view.tv_sinopseCardDetail.text = detail.sinopse

        return view
    }
}