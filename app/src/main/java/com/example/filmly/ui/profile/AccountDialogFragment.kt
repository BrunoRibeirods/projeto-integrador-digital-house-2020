package com.example.filmly.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmly.R
import kotlinx.android.synthetic.main.fragment_account_dialog.view.*

class AccountDialogFragment : DialogFragment() {
    val viewModel: AccountDialogViewModel by viewModels()
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
        val view = inflater.inflate(R.layout.fragment_account_dialog, container, false)

        view.toolbar_dialog.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        view.btn_save.setOnClickListener { view ->
            viewModel.navigateToProfileFragment()
        }

        viewModel.navigateToProfileFragment.observe(viewLifecycleOwner) {
            it?.let {
                val action = AccountDialogFragmentDirections.actionAccountDialogFragmentToProfileFragment(true)
                findNavController().navigate(action)
                viewModel.doneNavigating()
            }
        }

        return view
    }
}