package com.example.filmly.ui.yourLists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.YourListsAdapter
import com.example.filmly.repository.ServicesRepository
import kotlinx.android.synthetic.main.fragment_your_lists.view.*

class YourListsFragment : Fragment() {
    private lateinit var repository: ServicesRepository
    private val viewModel: YourListsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return YourListsViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_your_lists, container, false)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        repository = ServicesRepository.getInstance(requireContext())
        viewModel.refreshLists()
        Log.i("Antes", "passou")

            val yourListsAdapter = YourListsAdapter(viewModel, YourListsAdapter.SeeMoreNavigation { headLists ->
            val action =
                YourListsFragmentDirections.actionYourListsFragmentToFavoriteListsFragment(
                    headLists
                )
            findNavController().navigate(action)
        })
        view.rv_yourLists.apply {
            adapter = yourListsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.favoriteFilms.observe(viewLifecycleOwner) {
            viewModel.refreshHeadLists()
        }

        viewModel.favoriteSeries.observe(viewLifecycleOwner) {
            viewModel.refreshHeadLists()
        }

        viewModel.favoriteActors.observe(viewLifecycleOwner) {
            viewModel.refreshHeadLists()
        }

        viewModel.headLists.observe(viewLifecycleOwner) {
            it?.let {
                yourListsAdapter.data = it
                view.rv_yourLists.adapter = yourListsAdapter
            }
        }
        Log.i("Depois", "não passou")

        return view
    }
}