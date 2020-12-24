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
import kotlinx.android.synthetic.main.item_view_more.view.*

class ViewMoreAdapter(
    val cardInfo: Int,
    val cardNavigation: CardDetailNavigation
) : ListAdapter<Card, ViewMoreAdapter.CardListViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        return CardListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        val item = getItem(position)
        val cardDetail = CardDetail(cardInfo, item)

        holder.bind(item, cardDetail, cardNavigation)
    }

    class CardListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun from(parent: ViewGroup): CardListViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)

                return CardListViewHolder(view)
            }
        }
        fun bind(item: Card, cardDetail: CardDetail, cardNavigation: CardDetailNavigation) {
            view.tv_filme_view_more.text = item.name

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(view.resources.getColor(R.color.color_main))
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.image}")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_filme_view_more)

            view.card_item_view_more.setOnClickListener {
                cardNavigation.onClick(cardDetail)
            }
        }
    }
}
