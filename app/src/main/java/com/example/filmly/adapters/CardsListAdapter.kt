package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.domain.Card
import kotlinx.android.synthetic.main.cards_list_item.view.*

class CardsListAdapter(val data: List<Card>, val cardNavigation: CardDetailNavigation) : RecyclerView.Adapter<CardsListAdapter.CardsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
        return CardsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardsListViewHolder, position: Int) {
        val item = data[position]
        holder.view.iv_cardImage.setImageResource(item.image)

        holder.view.iv_cardImage.setOnClickListener {
            cardNavigation.onClick(item.id)
        }
    }

    override fun getItemCount(): Int = data.size

    class CardsListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class CardDetailNavigation(val click: (Int) -> Unit) {
        fun onClick(id: Int) = click(id)
    }
}