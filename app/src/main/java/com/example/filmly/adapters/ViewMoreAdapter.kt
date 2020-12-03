package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
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
        holder.imgCard.setImageResource(currentItem.image)
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
