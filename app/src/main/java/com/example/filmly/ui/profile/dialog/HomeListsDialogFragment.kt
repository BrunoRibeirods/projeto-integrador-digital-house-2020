package com.example.filmly.ui.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        updatePosition(view)
        view.mcb_filmes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToGenderList("films")
            } else {
                viewModel.removeFromGenderList("films")
            }
            updatePosition(view)
        }

        view.btn_save_home_configuration.setOnClickListener {
            if (!allChecked(view)) {
                Toast.makeText(context, "Selecione todas as caixas", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendHomeOrderList()
                viewModel.showChangesToast()
                findNavController().navigateUp()
            }
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

        view.mcb_trending.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToGenderList("trending")
            } else {
                viewModel.removeFromGenderList("trending")
            }
            updatePosition(view)
        }

        return view
    }

    fun updatePosition(view: View) {
        view.tv_film_position.text = (viewModel.modelList.indexOf("films") + 1).toString()
        view.tv_serie_position.text = (viewModel.modelList.indexOf("series") + 1).toString()
        view.tv_actor_position.text = (viewModel.modelList.indexOf("actors") + 1).toString()
        view.tv_trending_position.text = (viewModel.modelList.indexOf("trending") + 1).toString()
    }

    private fun allChecked(view: View): Boolean {
        view.apply {
            return mcb_filmes.isChecked && mcb_series.isChecked && mcb_actors.isChecked && mcb_trending.isChecked
        }
    }
}