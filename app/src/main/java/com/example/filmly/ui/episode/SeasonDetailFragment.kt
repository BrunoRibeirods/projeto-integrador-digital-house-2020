package com.example.filmly.ui.episode

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.filmly.R
import com.example.filmly.adapters.SeasonEpisodeAdapter
import com.example.filmly.data.model.FastBlur
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.ui.cardDetail.TvEpisodes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.dialog_episode_detail.*
import kotlinx.android.synthetic.main.fragment_card_detail.view.*
import kotlinx.android.synthetic.main.fragment_season_detail.*
import kotlinx.android.synthetic.main.fragment_season_detail.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class SeasonDetailFragment : Fragment(), SeasonEpisodeAdapter.OnClickEpisodeListener {

    private lateinit var repository: ServicesRepository
    private lateinit var episodes: TvEpisodes


    private val viewModel: SeasonDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SeasonDetailViewModel(repository) as T
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_season_detail, container, false)
        repository = ServicesRepository.getInstance(requireContext())
        val season_id: Int
        val season_number: Int

        view.toolbar_season_detail.setNavigationOnClickListener { activity?.onBackPressed() }

        arguments?.getInt("id").let {
            season_id = it!!
        }

        arguments?.getInt("season_number").let {
            season_number = it!!
        }

        viewModel.getEpisodeTvDetail(season_id, season_number)

        viewModel.tvEpisodesLive.observe(viewLifecycleOwner){
            rc_season_detail.apply {
                adapter = it.episodes?.let { it1 -> SeasonEpisodeAdapter(
                    it1,
                    this@SeasonDetailFragment
                ) }
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
            }
        }


        return view
    }

    private fun showDialog(title: String?, url: String?, overview: String?, date: String?, votes: Double?, number: String?) {

        //Inicializa o Dialog
        val dialog = Dialog(requireContext(), WindowManager.LayoutParams.MATCH_PARENT)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_episode_detail)

        //Componentes do layout
        val titleEp = dialog.findViewById<TextView>(R.id.title_episode_dialog)
        val overviewEp = dialog.findViewById<TextView>(R.id.overview_episode_dialog)
        val ivEp = dialog.findViewById<ImageView>(R.id.iv_episode_dialog)
        val ivEp_blur = dialog.findViewById<ImageView>(R.id.iv_episode_blur_dialog)
        val appbar = dialog.findViewById<AppBarLayout>(R.id.app_bar_dialog)
        val close = dialog.findViewById<FloatingActionButton>(R.id.close_btn)
        val fab = dialog.findViewById<FloatingActionButton>(R.id.dialog_vote)
        val epNumDetail = dialog.findViewById<TextView>(R.id.season_episode_dialog)
        val dateEp = dialog.findViewById<TextView>(R.id.air_date_episode_dialog)



        //Formatação da data de lançamento
        if(date != null){
            val inputFormatter = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val outputFormatter = DateTimeFormatter.ofPattern(
                "dd MMMM, yyyy",
                Locale.forLanguageTag("pt")
            )
            val date1 = LocalDate.parse(date, inputFormatter)
            val formattedDate = outputFormatter.format(date1)
            dateEp.text = formattedDate
        }else{
            dateEp.text = "Não disponivel"
        }

        //Preenche os dados retornados do Episodio
        overviewEp.text = overview
        titleEp.text = title
        epNumDetail.text = number



        //Transforma Texto em Img
        fab.setTextBitmap(votes.toString(), 65f, Color.WHITE)
        fab.scaleType = ImageView.ScaleType.CENTER
        fab.adjustViewBounds = false


        //Verifica a qualidade dos Episodios
        when(votes!!){
            in 7.0..10.0 -> {
                fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
                fab.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
            }
            in 5.5..6.9 -> {
                fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.orange))
                fab.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.orange))
            }
            else -> {
                fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
                fab.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
            }
        }


        //Verifica a versão do android para o alinhamento de texto
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            overviewEp.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        //Controle do blur da ImageView ligado ao Scroll
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offsetAlpha: Float = appBarLayout.y / appBarLayout.totalScrollRange
            ivEp_blur.alpha = offsetAlpha * -1
        })

        dialog.context.let {
            Glide.with(it).asBitmap()
                .load("https://image.tmdb.org/t/p/w500${url}")
                .error(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivEp.setImageBitmap(resource)
                        ivEp_blur.setImageBitmap(FastBlur.doBlur(resource, 10, false))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }


        //Botão Float que finaliza o dialog
        close.setOnClickListener { dialog.dismiss() }

        //Abre o Dialog
        dialog.show()

    }

    override fun onClickEpisode(position: Int) {
        viewModel.tvEpisodesLive.observe(viewLifecycleOwner){
            it?.episodes.let {listEps->
                if (listEps != null) {
                    episodes = listEps[position]
                    val season = if(episodes.season_number!! < 10) "S0" + episodes.season_number else "S" + episodes.season_number
                    val ep = if(episodes.episode_number!! < 10) "E0" + episodes.episode_number else "E" + episodes.episode_number
                    showDialog(episodes.name, episodes.still_path, episodes.overview, episodes.air_date, episodes.vote_average, "$season | $ep")
                }

            }
        }


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