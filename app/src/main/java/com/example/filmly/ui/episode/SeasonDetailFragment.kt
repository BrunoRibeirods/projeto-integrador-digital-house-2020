package com.example.filmly.ui.episode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.SeasonEpisodeAdapter
import com.example.filmly.data.model.CardDetail
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.ui.cardDetail.CardDetailViewModel
import kotlinx.android.synthetic.main.fragment_season_detail.*
import kotlinx.android.synthetic.main.fragment_season_detail.view.*


class SeasonDetailFragment : Fragment() {

    private lateinit var repository: ServicesRepository


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
                adapter = it.episodes?.let {it1 -> SeasonEpisodeAdapter(it1) }
                layoutManager = LinearLayoutManager(view.context)
                setHasFixedSize(true)
            }
        }


        return view
    }


}