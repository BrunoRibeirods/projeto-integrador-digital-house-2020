package com.filmly.app.adapters

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.filmly.app.R
import com.filmly.app.ui.cardDetail.TvEpisodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_season_detail.view.*

class SeasonEpisodeAdapter(private val listOfEpisodes: List<TvEpisodes>, val listener: OnClickEpisodeListener, val watchedListener: OnClickWatchListener): RecyclerView.Adapter<SeasonEpisodeAdapter.EpisodesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
       val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_season_detail, parent, false)

        return EpisodesViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val current = listOfEpisodes[position]


        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.setColorSchemeColors(
            holder.itemView.context.resources.getColor(R.color.color_main)
        )
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        holder.seasonNumEpisode.text = if(current.season_number!! < 10) "S0" + current.season_number else "S" + current.season_number
        holder.numberOfEpisode.text = if(current.episode_number!! < 10) "E0" + current.episode_number else "E" + current.episode_number
        holder.titleEpisode.text = current.name

        holder.btn_watched.imageTintList = if(current.watch == true) ColorStateList.valueOf(holder.itemView.resources.getColor(R.color.yellow)) else ColorStateList.valueOf(holder.itemView.resources.getColor(R.color.black))



        Glide.with(holder.itemView).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${current.still_path}")
            .placeholder(circularProgressDrawable)
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageEpisode)

    }

    override fun getItemCount() = listOfEpisodes.size

    override fun getItemId(position: Int): Long = position.toLong()

    interface OnClickEpisodeListener{
        fun onClickEpisode(position: Int)
    }

    interface OnClickWatchListener{
        fun onClickWatch(position: Int)
    }

    inner class EpisodesViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        init {
            view.setOnClickListener(this)
            view.btn_episode_watched.setOnClickListener(this)
        }

        val numberOfEpisode = view.tv_season_detail_episode
        val seasonNumEpisode = view.tv_season_detail_number
        val titleEpisode = view.tv_season_detail_title
        val imageEpisode = view.iv_season_detail
        val btn_watched = view.btn_episode_watched

        override fun onClick(p0: View?) {
            when(p0!!.id){
                R.id.btn_episode_watched ->{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        watchedListener.onClickWatch(position)
                }
                else -> {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        listener.onClickEpisode(position)
                }
            }
        }
    }


}

