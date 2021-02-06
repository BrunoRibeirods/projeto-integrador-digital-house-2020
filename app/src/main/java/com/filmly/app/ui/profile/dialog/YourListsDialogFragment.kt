package com.filmly.app.ui.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.filmly.app.R
import kotlinx.android.synthetic.main.fragment_your_lists_dialog.view.*

class YourListsDialogFragment : DialogFragment() {
    val viewModel: YourListsDialogViewModel by viewModels()
//
//    override fun onStart() {
//        super.onStart()
//
//        val dialog = dialog
//
//        dialog?.let {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog.window?.setLayout(width, height)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_your_lists_dialog, container, false)

        view.toolbar_your_lists_dialog.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        view.btn_save_yourLists_configuration.setOnClickListener {
            if (!allChecked(view)) {
                Toast.makeText(context, "Selecione todas as caixas", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendYourList()
                viewModel.showChangesToast()
                findNavController().navigateUp()
            }
        }

        updatePosition(view)
        view.mcb_listTitle1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToYourList("films")
            } else {
                viewModel.removeFromYourList("films")
            }
            updatePosition(view)
        }

        view.mcb_listTitle2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToYourList("series")
            } else {
                viewModel.removeFromYourList("series")
            }
            updatePosition(view)
        }

        view.mcb_listTitle3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToYourList("actors")
            } else {
                viewModel.removeFromYourList("actors")
            }
            updatePosition(view)
        }

        return view
    }

    fun updatePosition(view: View) {
        view.tv_position_listTitle1_configuration.text = (viewModel.modelList.indexOf("films") + 1).toString()
        view.tv_position_listTitle2_configuration.text = (viewModel.modelList.indexOf("series") + 1).toString()
        view.tv_position_listTitle3_configuration.text = (viewModel.modelList.indexOf("actors") + 1).toString()
    }

    private fun allChecked(view: View): Boolean {
        view.apply {
            return mcb_listTitle1.isChecked && mcb_listTitle2.isChecked && mcb_listTitle3.isChecked
        }
    }
}