package com.example.filmly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.domain.Serie
import kotlinx.android.synthetic.main.cards_list_item.view.*

class TopSeriesAdapter(val data: List<Serie>) : RecyclerView.Adapter<TopSeriesAdapter.TopSeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
        return TopSeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopSeriesViewHolder, position: Int) {
        val item = data[position]
        holder.view.iv_cardImage.setImageResource(item.image)
    }

    override fun getItemCount(): Int = data.size

    class TopSeriesViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}