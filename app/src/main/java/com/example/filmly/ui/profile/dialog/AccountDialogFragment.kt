package com.example.filmly.ui.profile.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmly.R
import com.example.filmly.data.model.UserInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_account_dialog.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

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

        view.btn_save.setOnClickListener {
            saveInformation(view)
            viewModel.showChangesToast()
            viewModel.navigateToProfileFragment()
        }

        viewModel.navigateToProfileFragment.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigateUp()
                viewModel.doneNavigating()
            }
        }



        return view
    }

    fun saveInformation(view: View) {
        val name = view.et_name_configuration.text.toString()
        val email = view.et_email_configuration.text.toString()
        val birthday = view.et_dateOfBirth_configuration.text.toString()
        val password = view.et_changePassword_configuration.text.toString()


        viewModel.saveInformation(UserInformation(name, email, null, birthday, password))
    }

}