package com.example.filmly.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmly.repository.StatesRepository

class ProfileViewModel : ViewModel() {
    val searchItems = arrayOf("Dia", "Semana")

    val showChangesToast = StatesRepository.showChangesToast

    private val _searchItemSelected = MutableLiveData(0)
    val searchItemSelected: LiveData<Int>
        get() = _searchItemSelected

    private val _navigateToAccountDialog = MutableLiveData<Boolean>()
    val navigateToAccountDialog: LiveData<Boolean>
        get() = _navigateToAccountDialog

    fun updateSearchItem(value: Int) {
        _searchItemSelected.value = value
    }

    fun navigateToAccountDialog() {
        _navigateToAccountDialog.value = true
    }

    fun doneNavigating() {
        _navigateToAccountDialog.value = null
    }

    fun doneShowingToast() {
        StatesRepository.doneShowingToast()
    }

    fun showChangesToast() {
        StatesRepository.showChangesToast()
    }
}