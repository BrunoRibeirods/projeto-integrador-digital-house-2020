package com.filmly.app.ui.profile.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.filmly.app.R
import com.filmly.app.data.model.UserInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_account_dialog.view.*

class AccountDialogFragment : DialogFragment() {
    val viewModel: AccountDialogViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

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
        auth = FirebaseAuth.getInstance()

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
        val user = auth.currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(view.et_name_configuration.text.toString())
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("", "User profile updated.")
                }
            }


        viewModel.saveInformation(UserInformation(user?.displayName, user?.email, user?.photoUrl.toString(), null, null))
    }

}