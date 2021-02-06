package com.filmly.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.filmly.app.R
import com.filmly.app.ui.cardDetail.TvSeasonResults
import kotlinx.android.synthetic.main.item_serie_season.view.*

class CardDetailListsAdapter(private val listDetailCard: List<TvSeasonResults>, val listener: OnClickSeasonListener): RecyclerView.Adapter<CardDetailListsAdapter.CardDetailListsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailListsViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_serie_season, parent, false)

        return CardDetailListsViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: CardDetailListsViewHolder, position: Int) {
        val current = listDetailCard[position]



        holder.num_season_item.text = if (current.season_number == 0) "E" else current.season_number.toString()

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.setColorSchemeColors(
            holder.itemView.context.resources.getColor(R.color.color_main)
        )
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.itemView).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${current.poster_path}")
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(holder.iv_season_item)


    }

    override fun getItemCount() = listDetailCard.size

    interface OnClickSeasonListener{
        fun onClickSeason(position: Int)
    }

    inner class CardDetailListsViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        init {
            view.setOnClickListener(this)
        }

        val num_season_item = view.tv_season_number
        val iv_season_item = view.iv_season

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onClickSeason(position)
        }
    }




}