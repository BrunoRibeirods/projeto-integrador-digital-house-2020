package com.example.filmly.ui.profile.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmly.data.model.UserInformation
import com.example.filmly.repository.StatesRepository

class AccountDialogViewModel : ViewModel() {

    private val _navigateToProfileFragment = MutableLiveData<Boolean>()
    val navigateToProfileFragment: LiveData<Boolean>
        get() = _navigateToProfileFragment

    fun navigateToProfileFragment() {
        _navigateToProfileFragment.value = true
    }

    fun doneNavigating() {
        _navigateToProfileFragment.value = null
    }

    fun saveInformation(user: UserInformation) {
        StatesRepository.updateUserInformation(user)
    }

    fun showChangesToast() {
        StatesRepository.showChangesToast()
    }
}