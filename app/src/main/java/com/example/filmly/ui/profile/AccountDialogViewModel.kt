package com.example.filmly.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}