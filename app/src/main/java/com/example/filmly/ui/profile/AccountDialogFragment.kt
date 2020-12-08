package com.example.filmly.ui.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.filmly.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_account_dialog.view.*

class AccountDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()

        val dialog = dialog

        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_dialog, container, false)

        view.toolbar_dialog.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        view.tv_change_email.setOnClickListener {
            context?.let { context ->
                MaterialAlertDialogBuilder(context)
                    .setTitle("Title")
                    .setMessage("Make your wish")
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setNegativeButton("Decline") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("Accept") { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_account_configuration, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                Toast.makeText(context, "Itens salvos!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false

        }
    }
}