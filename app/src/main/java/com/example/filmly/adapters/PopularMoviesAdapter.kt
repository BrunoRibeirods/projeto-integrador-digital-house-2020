package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.filmly.R
import com.example.filmly.data.model.CardDetail
import com.example.filmly.ui.home.PopularMovie
import com.example.filmly.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.cards_list_item.view.*

class PopularMoviesAdapter(
    val cardInfo: Int,
    val cardNavigation: CardDetailNavigation,
) : PagingDataAdapter<PopularMovie, PopularMoviesAdapter.PopularMoviesViewHolder>(
    POPULAR_MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return PopularMoviesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, cardInfo, cardNavigation)
        }
    }

    companion object {
        private val POPULAR_MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<PopularMovie>() {
            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem == newItem
        }
    }

    class PopularMoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: PopularMovie, cardInfo: Int, cardNavigation: CardDetailNavigation) {

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(
                view.resources.getColor(R.color.color_main)
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_cardImage)

            val cardDetail = CardDetail(cardInfo, item.convertToFilm())
            view.iv_cardImage.setOnClickListener {
                cardNavigation.onClick(cardDetail)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PopularMoviesViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
                return PopularMoviesViewHolder(view)
            }
        }
    }
}

class PopularTVAdapter(
    val cardInfo: Int,
    val cardNavigation: CardDetailNavigation,
) : PagingDataAdapter<PopularMovie, PopularTVAdapter.PopularTVViewHolder>(
    POPULAR_TV_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTVViewHolder {
        return PopularTVViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PopularTVViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, cardInfo, cardNavigation)
        }
    }

    companion object {
        private val POPULAR_TV_COMPARATOR = object : DiffUtil.ItemCallback<PopularMovie>() {
            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem == newItem
        }
    }

    class PopularTVViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: PopularMovie, cardInfo: Int, cardNavigation: CardDetailNavigation) {

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(
                view.resources.getColor(R.color.color_main)
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_cardImage)

            val cardDetail = CardDetail(cardInfo, item.convertToFilm())
            view.iv_cardImage.setOnClickListener {
                cardNavigation.onClick(cardDetail)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PopularTVViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
                return PopularTVViewHolder(view)
            }
        }
    }
}

class PopularActorsAdapter(
    val cardInfo: Int,
    val cardNavigation: CardDetailNavigation,
) : PagingDataAdapter<PopularMovie, PopularActorsAdapter.PopularActorsViewHolder>(
    POPULAR_ACTOR_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularActorsViewHolder {
        return PopularActorsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PopularActorsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, cardInfo, cardNavigation)
        }
    }

    companion object {
        private val POPULAR_ACTOR_COMPARATOR = object : DiffUtil.ItemCallback<PopularMovie>() {
            override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean =
                oldItem == newItem
        }
    }

    class PopularActorsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: PopularMovie, cardInfo: Int, cardNavigation: CardDetailNavigation) {

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.setColorSchemeColors(
                view.resources.getColor(R.color.color_main)
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .into(view.iv_cardImage)

            val cardDetail = CardDetail(cardInfo, item.convertToFilm())
            view.iv_cardImage.setOnClickListener {
                cardNavigation.onClick(cardDetail)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PopularActorsViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_list_item, parent, false)
                return PopularActorsViewHolder(view)
            }
        }
    }
}
