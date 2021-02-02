package com.example.filmly.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmly.R
import com.example.filmly.data.model.UserInformation
import com.example.filmly.repository.StatesRepository
import com.example.filmly.ui.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    val viewModel: ProfileViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()
        updateUI(auth.currentUser)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        viewModel.showChangesToast.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, "Alterações salvas", Toast.LENGTH_SHORT).show()
                viewModel.doneShowingToast()
            }
        }

        view.btn_account_configuration.setOnClickListener {
            viewModel.navigateToAccountDialog()
        }

        viewModel.navigateToAccountDialog.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(R.id.action_profileFragment_to_accountDialogFragment)
                viewModel.doneNavigating()
            }
        }

        view.btn_search_configuration.setOnClickListener {
            val DAY_SELECTED = 0
            val WEEK_SELECTED = 1

            var positionSelected = 0

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Frequência de busca")
                .setNeutralButton("Cancelar") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("Ok") { dialog, which ->
                    // Respond to positive button press
                    viewModel.updateSearchItem(positionSelected)
                    viewModel.updateSearchPreference(positionSelected)
                    viewModel.showChangesToast()
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(viewModel.searchItems, viewModel.searchItemSelected.value!!) { dialog, which ->
                    // Respond to item chosen
                    positionSelected = which
                }
                .show()
        }

        view.btn_home_configuration.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeListsDialogFragment)
        }

        view.btn_yourLists_configuration.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_yourListsDialogFragment)
        }

        view.btn_signOut.setOnClickListener { signOut() }

        Glide.with(view)
            .load(StatesRepository.userInformation.value?.img)
            .error(R.drawable.profile_placeholder)
            .fallback(R.drawable.profile_placeholder)
            .into(view.circleImageView)

        return view
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
        updateUI(null)
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            viewModel.saveInformation(UserInformation(user.displayName.toString(), user.email.toString(), user.photoUrl.toString(), null, null))
        } else {
            Log.i("Account", "Nenhum usuario conectado.")
        }
    }
}