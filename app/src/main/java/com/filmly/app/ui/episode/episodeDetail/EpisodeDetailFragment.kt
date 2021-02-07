package com.filmly.app.ui.episode.episodeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.filmly.app.R
import com.filmly.app.adapters.EpisodeViewPageAdapter
import com.filmly.app.ui.cardDetail.TvEpisodes
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_episode_detail.view.*


class EpisodeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_episode_detail, container, false)



        arguments?.get("Episodios").let {
            it as List<TvEpisodes>

            view.pager.adapter = EpisodeViewPageAdapter(it)

            TabLayoutMediator(view.tabs, view.pager) { tab, position ->

            }.attach()
        }



        return view
    }






}