package com.example.filmly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.domain.Actor
import kotlinx.android.synthetic.main.cards_list_item.view.*

class TopActorsAdapter(val data: List<Actor>) : RecyclerView.Adapter<TopActorsAdapter.TopActorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopActorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
        return TopActorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopActorsViewHolder, position: Int) {
        val item = data[position]
        holder.view.iv_cardImage.setImageResource(item.image)
    }

    override fun getItemCount(): Int = data.size

    class TopActorsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}