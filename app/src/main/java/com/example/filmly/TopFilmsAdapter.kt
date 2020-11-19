package com.example.filmly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.domain.Film
import kotlinx.android.synthetic.main.cards_list_item.view.*

class TopFilmsAdapter(val data: List<Film>) : RecyclerView.Adapter<TopFilmsAdapter.TopFilmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
        return TopFilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val item = data[position]
        holder.view.iv_cardImage.setImageResource(item.image)
    }

    override fun getItemCount(): Int = data.size

    class TopFilmsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}