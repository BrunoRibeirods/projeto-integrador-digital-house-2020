package com.example.filmly.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmly.R
import kotlinx.android.synthetic.main.fragment_home_lists_dialog.view.*

class HomeListsDialogFragment : DialogFragment() {
    val viewModel: HomeListsDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_lists_dialog, container, false)

        view.toolbar_home_dialog.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        view.mcb_filmes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToGenderList("films")
            } else {
                viewModel.removeFromGenderList("films")
            }
            updatePosition(view)
        }

        view.mcb_series.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToGenderList("series")
            } else {
                viewModel.removeFromGenderList("series")
            }
            updatePosition(view)
        }

        view.mcb_actors.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToGenderList("actors")
            } else {
                viewModel.removeFromGenderList("actors")
            }
            updatePosition(view)
        }

        return view
    }

    fun updatePosition(view: View) {
        view.tv_film_position.text = (viewModel.genderList.indexOf("films") + 1).toString()
        view.tv_serie_position.text = (viewModel.genderList.indexOf("series") + 1).toString()
        view.tv_actor_position.text = (viewModel.genderList.indexOf("actors") + 1).toString()
    }
}