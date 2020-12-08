package com.example.filmly.ui.cardDetail

import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
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

        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        val detail = arguments?.getSerializable("detail") as CardDetail

        view.tv_titleDetail.text = detail.title

        Glide.with(view).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${detail.cardImage}")
            .placeholder(circularProgressDrawable)
            .error(circularProgressDrawable)
            .fallback(circularProgressDrawable)
            .into(view.iv_cardDetailImage)

        view.tv_sinopseCardDetail.text = detail.sinopse

        return view
    }
}