package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.filmly.R
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.utils.CardDetailNavigation
import com.example.filmly.utils.CardDiffCallback
import kotlinx.android.synthetic.main.cards_list_item.view.*


class CardsListAdapter(
    val cardInfo: Int,
    val cardNavigation: CardDetailNavigation,
) : ListAdapter<Card, CardsListAdapter.CardsListViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListViewHolder {
        return CardsListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, cardInfo, cardNavigation)
    }

    class CardsListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Card, cardInfo: Int, cardNavigation: CardDetailNavigation) {

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(
                view.resources.getColor(R.color.color_main)
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.image}")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_cardImage)

            val cardDetail = CardDetail(cardInfo, item)
            view.iv_cardImage.setOnClickListener {
                cardNavigation.onClick(cardDetail)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CardsListViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
                return CardsListViewHolder(view)
            }
        }
    }
}