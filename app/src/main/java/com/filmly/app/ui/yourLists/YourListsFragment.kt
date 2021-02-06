package com.filmly.app.ui.yourLists

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
import com.bumptech.glide.Glide
import com.filmly.app.R
import com.filmly.app.adapters.YourListsAdapter
import com.filmly.app.data.model.UserInformation
import com.filmly.app.repository.ServicesRepository
import com.filmly.app.repository.StatesRepository
import com.filmly.app.utils.SeeMoreNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_your_lists.view.*
import kotlinx.android.synthetic.main.fragment_your_lists.view.civ_profileImage

class YourListsFragment : Fragment() {
    private lateinit var repository: ServicesRepository
    private lateinit var auth: FirebaseAuth
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
        auth = FirebaseAuth.getInstance()
        updateUI(auth.currentUser)

        Glide.with(view)
            .load(auth.currentUser?.photoUrl)
            .error(R.drawable.profile_placeholder)
            .fallback(R.drawable.profile_placeholder)
            .into(view.civ_profileImage)


        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        repository = ServicesRepository.getInstance(requireContext())
        viewModel.refreshLists()

        val yourListsAdapter = YourListsAdapter(SeeMoreNavigation { headLists ->
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
                yourListsAdapter.submitList(it)
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            StatesRepository.updateUserInformation(UserInformation(user.displayName.toString(), user.email.toString(), user.photoUrl.toString(), null, null))
        } else {
            Log.i("Account", "Nenhum usuario conectado.")
        }
    }


}