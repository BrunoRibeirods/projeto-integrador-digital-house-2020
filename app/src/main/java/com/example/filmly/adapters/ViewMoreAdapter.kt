package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmly.R
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import kotlinx.android.synthetic.main.cards_list_item.view.*
import kotlinx.android.synthetic.main.item_view_more.view.*

class ViewMoreAdapter(private val listaDeCards: List<Card>, val cardNavigation: CardDetailNavigation): RecyclerView.Adapter<ViewMoreAdapter.CardListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)

        return CardListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        val currentItem = listaDeCards[position]
        val cardDetail = CardDetail(currentItem.name, currentItem.image, currentItem.descricao)

        holder.title.text = currentItem.name

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.setColorSchemeColors(holder.itemView.resources.getColor(R.color.color_main).toInt())
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.itemView).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${currentItem.image}")
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(holder.imgCard)

        holder.cardFull.setOnClickListener {
            cardNavigation.onClick(cardDetail)
        }
    }

    override fun getItemCount() = listaDeCards.size

    class CardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.tv_filme_view_more
        val imgCard = itemView.iv_filme_view_more
        val cardFull = itemView.card_item_view_more
    }

    class CardDetailNavigation(val click: (CardDetail) -> Unit) {
        fun onClick(card: CardDetail) = click(card)
    }
}
