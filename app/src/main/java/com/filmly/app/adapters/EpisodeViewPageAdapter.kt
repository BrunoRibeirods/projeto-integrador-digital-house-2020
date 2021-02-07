package com.filmly.app.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.filmly.app.R
import com.filmly.app.data.model.FastBlur
import com.filmly.app.ui.cardDetail.TvEpisodes
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_episode_detail.view.*
import kotlinx.android.synthetic.main.item_pager_episode.view.*
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

class EpisodeViewPageAdapter(val listaEp: List<TvEpisodes>): RecyclerView.Adapter<EpisodeViewPageAdapter.PageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pager_episode,
            parent,
            false
        )

        return PageHolder(view)
    }

    override fun getItemCount(): Int = listaEp.size

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        val current = listaEp[position]


        //Controle do blur da ImageView ligado ao Scroll
        holder.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offsetAlpha: Float = appBarLayout.y / appBarLayout.totalScrollRange
            holder.epImageBlur.alpha = offsetAlpha * -1
        })


        Glide.with(holder.itemView).asBitmap()
            .load("https://image.tmdb.org/t/p/w500${current.still_path}")
            .error(R.drawable.placeholder)
            .fallback(R.drawable.placeholder)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    holder.epImage.setImageBitmap(resource)
                    holder.epImageBlur.setImageBitmap(FastBlur.doBlur(resource, 10, false))
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

            })

        holder.titleEp.text = current.name
        holder.seasonNum.text = if(current.episode_number!! < 10) "EP0" + current.episode_number else "EP" + current.episode_number
        if(!TextUtils.isEmpty(current.overview)) holder.overviewEp.text = current.overview else holder.overviewEp.text = "Descrição não disponivel no momento."

        val fmt = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = fmt.parse(current.air_date)

        val fmtOut = SimpleDateFormat("dd MMMM, yyyy", Locale.forLanguageTag("pt"))


        holder.airDateEp.text = fmtOut.format(date)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.overviewEp.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }




        //Transforma Texto em Img
        holder.fab.setTextBitmap(current.vote_average.toString(), 65f, Color.WHITE)
        holder.fab.scaleType = ImageView.ScaleType.CENTER
        holder.fab.adjustViewBounds = false


        //Verifica a qualidade dos Episodios
        when(current.vote_average.toString().toDouble()){
            in 7.0..10.0 -> {
                holder.fab.backgroundTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.green
                    )
                )
                holder.fab.imageTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.green
                    )
                )
            }
            in 5.5..6.9 -> {
                holder.fab.backgroundTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.orange
                    )
                )
                holder.fab.imageTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.orange
                    )
                )
            }
            else -> {
                holder.fab.backgroundTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.red
                    )
                )
                holder.fab.imageTintList = ColorStateList.valueOf(
                    holder.itemView.resources.getColor(
                        R.color.red
                    )
                )
            }
        }


    }

    class PageHolder(root: View): RecyclerView.ViewHolder(root) {
        val appbar = root.app_bar_dialog
        val epImage = root.iv_episode_dialog
        val epImageBlur = root.iv_episode_blur_dialog
        val titleEp = root.title_episode_dialog
        val seasonNum = root.season_episode_dialog
        val overviewEp = root.overview_episode_dialog
        val airDateEp = root.air_date_episode_dialog
        val fab = root.dialog_vote
    }


    private fun ImageView.setTextBitmap(text: String, textSize: Float, textColor: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.setColor(textColor)
        paint.textAlign = Paint.Align.LEFT
        val lines = text.split("\n")
        var maxWidth = 0
        for (line in lines) {
            val width = paint.measureText(line).toInt()
            if (width > maxWidth) {
                maxWidth = width
            }
        }
        val height = paint.descent() - paint.ascent()
        val bitmap = Bitmap.createBitmap(
            maxWidth,
            height.toInt() * lines.size,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        var y = - paint.ascent()
        for (line in lines) {
            canvas.drawText(line, 0f, y, paint)
            y += height
        }
        setImageBitmap(bitmap)
    }


}


