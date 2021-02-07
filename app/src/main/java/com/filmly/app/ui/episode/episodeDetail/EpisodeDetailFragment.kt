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


        val episodes = arguments?.getSerializable("Episodios") as List<TvEpisodes>
        val seasonNum = arguments?.getInt("Season")
        val pos = arguments?.getInt("position")

        if (seasonNum != null) {
            view.toolbar_episode_detail.title = if(seasonNum > 0)"Temporada " + seasonNum.toString() else "Especiais"
        }

        view.toolbar_episode_detail.setNavigationOnClickListener { activity?.onBackPressed() }


        view.pager.adapter = EpisodeViewPageAdapter(episodes)
        view.pager.setCurrentItem(pos!!, false)

        TabLayoutMediator(view.tabs, view.pager) { tab, position -> }.attach()




        return view
    }






}