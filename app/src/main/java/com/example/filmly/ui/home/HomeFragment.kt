package com.example.filmly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.filmly.R
import com.example.filmly.adapters.HomeListsAdapter
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import com.example.filmly.utils.SeeMoreNavigation
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var repository: ServicesRepository

    val viewModel by viewModels<HomeViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        repository = ServicesRepository.getInstance(requireContext())
        val homeAdapter = HomeListsAdapter(SeeMoreNavigation { trending ->
            val action = HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(trending)
            findNavController().navigate(action)
        })

        homeAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (!view.rv_homeLists.canScrollVertically(-1)) {
                    view.rv_homeLists.scrollToPosition(0)
                }
            }
        })

        view.rv_homeLists.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }


        viewModel.refreshLists()


        viewModel.getTrendingLive("all")
        viewModel.getTrendingLive("tv")
        viewModel.getTrendingLive("movie")
        viewModel.getTrendingLive("person")

        view.tv_greetings.text =
            getString(R.string.hello_wil, StatesRepository.userInformation.value?.name)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        viewModel.trendingLive.observe(viewLifecycleOwner) {
            homeAdapter.submitList(getResultsLists(it.results.map {
                when (it.media_type) {
                    "movie" -> it.convertToCard()
                    "tv" -> it.convertToTv()
                    "person" -> it.convertToPerson()
                    else -> it.convertToCard()
                }
            }, when (it.results.map { it.media_type }.distinct().joinToString()) {
                "movie" -> CardDetail.FILM
                "tv" -> CardDetail.SERIE
                "person" -> CardDetail.ACTOR
                else -> CardDetail.TRENDING
            }
            )
            )
        }

        return view
    }

    fun getResultsLists(listaFilmes: List<Card>, cardInfo: Int): List<HeadLists> {

        val tipo = when (cardInfo) {
            CardDetail.FILM -> "filmes"
            CardDetail.ACTOR -> "pessoas"
            CardDetail.SERIE -> "séries"
            CardDetail.TRENDING -> "trending"
            else -> "outros"
        }

        val timeString = when (StatesRepository.searchTime) {
            "day" -> "do dia"
            else -> "da semana"
        }

        val order = StatesRepository.homeListsOrder

        val roles: HashMap<String, Int> = hashMapOf(
            "Top trending $timeString" to order.indexOf("trending"),
            "Top séries $timeString" to order.indexOf("series"),
            "Top filmes $timeString" to order.indexOf("films"),
            "Top pessoas $timeString" to order.indexOf("actors")
        )

        viewModel.headLists.add(HeadLists("Top $tipo $timeString", listaFilmes, cardInfo))
        viewModel.headLists.sortWith(Comparator { t, t2 ->
            return@Comparator roles[t.titleMessage]!! - roles[t2.titleMessage]!!
        })

        return viewModel.headLists.distinctBy { it.titleMessage }
    }
}