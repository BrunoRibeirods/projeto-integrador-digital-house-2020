package com.filmly.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.filmly.app.R
import com.filmly.app.data.model.CardDetail
import com.filmly.app.ui.home.HomeActorNetwork
import com.filmly.app.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.item_mini_known_for_cards.view.*

class CastAdapter(val data: List<HomeActorNetwork>, val navigation: CardDetailNavigation) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mini_known_for_cards, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item = data[position]

        val circularProgressDrawable = CircularProgressDrawable(holder.view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.setColorSchemeColors(
            holder.view.resources.getColor(R.color.color_main)
        )
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(holder.view).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${item.profile_path}")
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .into(holder.view.iv_known_for)

        val cardDetail = CardDetail(CardDetail.ACTOR, item.convertToActor())
        holder.view.iv_known_for.setOnClickListener {
            navigation.onClick(cardDetail)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CastViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}