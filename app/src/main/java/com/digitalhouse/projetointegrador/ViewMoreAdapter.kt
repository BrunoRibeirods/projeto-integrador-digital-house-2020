package com.digitalhouse.projetointegrador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.projetointegrador.domain.ViewMoreCard
import kotlinx.android.synthetic.main.item_view_more.view.*

class ViewMoreAdapter(private val listaDeCards: List<ViewMoreCard>): RecyclerView.Adapter<ViewMoreAdapter.CardListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)

        return CardListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        val currentItem = listaDeCards[position]

        holder.title.text = currentItem.title
        holder.imgCard.setImageResource(currentItem.cardImg)
    }

    override fun getItemCount() = listaDeCards.size

    class CardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.tv_filme_view_more
        val imgCard = itemView.iv_filme_view_more
    }

}
