package com.filmly.app.ui.episode

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
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.filmly.app.R
import com.filmly.app.adapters.SeasonEpisodeAdapter
import com.filmly.app.data.model.FastBlur
import com.filmly.app.repository.ServicesRepository
import com.filmly.app.ui.cardDetail.TvEpisodes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_episode_detail.*
import kotlinx.android.synthetic.main.fragment_card_detail.view.*
import kotlinx.android.synthetic.main.fragment_season_detail.*
import kotlinx.android.synthetic.main.fragment_season_detail.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class SeasonDetailFragment : Fragment(), SeasonEpisodeAdapter.OnClickEpisodeListener, SeasonEpisodeAdapter.OnClickWatchListener {

    private lateinit var repository: ServicesRepository
    private lateinit var episodes: TvEpisodes
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter1: SeasonEpisodeAdapter
    private lateinit var cr: CollectionReference
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String

    var season_number: Int = 0

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
        auth = FirebaseAuth.getInstance()
        config()

        view.toolbar_season_detail.setNavigationOnClickListener { activity?.onBackPressed() }

        arguments?.getInt("id").let {
            season_id = it!!
        }

        arguments?.getInt("season_number").let {
            season_number = it!!
        }

        arguments?.getString("name").let {
            name = it!!
        }


        viewModel.getEpisodeTvDetail(season_id, season_number)

        viewModel.tvEpisodesLive.observe(viewLifecycleOwner){
            adapter1 = it.episodes?.let { it1 ->  SeasonEpisodeAdapter(it1, this@SeasonDetailFragment, this@SeasonDetailFragment) }!!
            rc_season_detail.apply {
                adapter = adapter1
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
            }
        }


        return view
    }


    override fun onClickEpisode(position: Int) {
        viewModel.tvEpisodesLive.observe(viewLifecycleOwner){
            it?.episodes.let {listEps->
                if (listEps != null) {
                    val bundle = bundleOf("Episodios" to listEps)
                    findNavController().navigate(R.id.action_seasonDetailFragment_to_episodeDetailFragment, bundle)
                }

            }
        }
    }


    fun config(){
        db = FirebaseFirestore.getInstance()
        cr = db.collection(auth.currentUser!!.uid.toString())
    }


    override fun onClickWatch(position: Int) {
        Toast.makeText(context, "Adicionado", Toast.LENGTH_SHORT).show()
    }



}