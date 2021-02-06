package com.filmly.app.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.filmly.app.R
import com.filmly.app.ui.cardDetail.Provider
import kotlinx.android.synthetic.main.item_serie_watch.view.*


class CardDetailProvidersAdapter(private val listProviderCard: List<Provider>): RecyclerView.Adapter<CardDetailProvidersAdapter.CardDetailProvidersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDetailProvidersViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_serie_watch, parent, false)

        return CardDetailProvidersViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: CardDetailProvidersViewHolder, position: Int) {
        val current = listProviderCard[position]

        holder.item_tv_provider.text = current.provider_name

        val circularProgressDrawable = CircularProgressDrawable(holder.view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.setColorSchemeColors(
            holder.view.resources.getColor(R.color.color_main)
        )
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.view).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${current.logo_path}")
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(holder.item_iv_provider)


    }

    override fun getItemCount() = listProviderCard.size

    class CardDetailProvidersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val item_iv_provider = view.iv_detail_provider_item
        val item_tv_provider = view.tv_detail_provider_item
    }

}