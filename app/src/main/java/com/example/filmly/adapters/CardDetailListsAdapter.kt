package com.example.filmly.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.filmly.R
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.FastBlur
import com.example.filmly.ui.cardDetail.TvSeasonResults
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.cards_list_item.view.*
import kotlinx.android.synthetic.main.fragment_card_detail.view.*
import kotlinx.android.synthetic.main.item_serie_season.view.*

class CardDetailListsAdapter(private val listDetailCard: List<TvSeasonResults>): RecyclerView.Adapter<CardDetailListsAdapter.CardDetailListsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailListsViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_serie_season, parent, false)

        return CardDetailListsViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: CardDetailListsViewHolder, position: Int) {
        val current = listDetailCard[position]



        holder.num_season_item.text = if (current.season_number == 0) "E" else current.season_number.toString()

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()



        ////////////////////////


        Glide.with(holder.itemView).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${current.poster_path}")
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(holder.iv_season_item)


        ////////////////////////////
    }

    override fun getItemCount() = listDetailCard.size

    class CardDetailListsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val num_season_item = view.tv_season_number
        val iv_season_item = view.iv_season
    }


}