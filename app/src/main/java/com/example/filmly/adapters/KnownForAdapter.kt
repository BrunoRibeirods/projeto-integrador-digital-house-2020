package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmly.R
import com.example.filmly.data.model.CardDetail
import com.example.filmly.ui.search.KnownFor
import com.example.filmly.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.item_mini_known_for_cards.view.*

class KnownForAdapter(val knownList: List<KnownFor>, val cardDetailNavigation: CardDetailNavigation): RecyclerView.Adapter<KnownForAdapter.KnownForViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        return KnownForViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: KnownForViewHolder, position: Int) {
        val item = knownList[position]
        holder.bind(item, cardDetailNavigation)
    }

    override fun getItemCount() = knownList.size

    class KnownForViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: KnownFor, navigation: CardDetailNavigation) {
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(
                view.context.resources.getColor(R.color.color_main)
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_known_for)

            view.iv_known_for.setOnClickListener {
                when (item.media_type) {
                    "tv" -> {
                        navigation.onClick(CardDetail(CardDetail.SERIE, item.convertToSerie()))
                    }
                    "movie" -> {
                        navigation.onClick(CardDetail(CardDetail.FILM, item.convertToFilm()))
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): KnownForViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mini_known_for_cards, parent, false)
                return KnownForViewHolder(view)
            }
        }
    }
}