package com.example.filmly.ui.viewMore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.PopularActorsAdapter
import com.example.filmly.adapters.PopularMoviesAdapter
import com.example.filmly.adapters.PopularTVAdapter
import com.example.filmly.adapters.ViewMoreAdapter
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.fragment_view_more.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ViewMoreFragment : Fragment() {
    private lateinit var repository: ServicesRepository

    val viewModel by viewModels<ViewMoreViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ViewMoreViewModel(repository) as T
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_more, container, false)
        val args = arguments?.getSerializable("headList") as HeadLists?

        if (args != null) {
            view.toolbar_viewMore.title = args.titleMessage
            view.rc_view_more.adapter = ViewMoreAdapter(args.cardInfo, CardDetailNavigation {
                val action = ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(it)
                findNavController().navigate(action)
            }).also { it.submitList(args.data) }

        } else {
            repository = ServicesRepository.getInstance(requireContext())
            val args = arguments?.getString("type")
            when (args) {
                "tv" -> {
                    view.toolbar_viewMore.title = "Séries em Alta"
                    val tvAdapter = PopularTVAdapter(CardDetail.SERIE, CardDetailNavigation { detail ->
                        findNavController().navigate(ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(detail))
                    })
                    view.rc_view_more.adapter = tvAdapter
                    lifecycleScope.launch {
                        viewModel.getAllPopularSeries().collect {
                            tvAdapter.submitData(it)
                        }
                    }
                }
                "movie" -> {
                    view.toolbar_viewMore.title = "Filmes em Alta"
                    val moviesAdapter = PopularMoviesAdapter(CardDetail.FILM, CardDetailNavigation { detail ->
                        findNavController().navigate(ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(detail))
                    })
                    view.rc_view_more.adapter = moviesAdapter
                    lifecycleScope.launch {
                        viewModel.getAllPopularMovies().collect {
                            moviesAdapter.submitData(it)
                        }
                    }
                }
                "person" -> {
                    view.toolbar_viewMore.title = "Atores em Alta"
                    val actorsAdapter = PopularActorsAdapter(CardDetail.ACTOR, CardDetailNavigation { detail ->
                        findNavController().navigate(ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(detail))
                    })
                    view.rc_view_more.adapter = actorsAdapter
                    lifecycleScope.launch {
                        viewModel.getAllPopularActors().collect {
                            actorsAdapter.submitData(it)
                        }
                    }
                }
            }
        }


        view.toolbar_viewMore.setNavigationOnClickListener {
            activity?.onBackPressed()
        }


//        val rc_viewMore = view.findViewById<RecyclerView>(R.id.rc_view_more)
//        rc_viewMore.adapter = ViewMoreAdapter(args.cardInfo, CardDetailNavigation {
//            val action = ViewMoreFragmentDirections.actionViewMoreFragmentToCardDetailFragment(it)
//            findNavController().navigate(action)
//        }).also { it.submitList(args.data) }

        view.rc_view_more.layoutManager = GridLayoutManager(context, 2)

        return view
    }

}