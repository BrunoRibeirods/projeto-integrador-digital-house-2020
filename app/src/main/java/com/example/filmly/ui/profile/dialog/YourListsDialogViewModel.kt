package com.example.filmly.ui.profile.dialog

import androidx.lifecycle.ViewModel
import com.example.filmly.repository.StatesRepository

class YourListsDialogViewModel : ViewModel() {
    val yourList = StatesRepository.yourListsOrder
    val modelList = mutableListOf<String>()

    init {
        yourList.forEach {
            modelList.add(it)
        }
    }

    fun addToYourList(typeList: String) {
        modelList.add(typeList)
    }

    fun removeFromYourList(typeList: String) {
        modelList.remove(typeList)
    }

    fun sendYourList() {
        StatesRepository.updateYourListsOrder(modelList)
    }

    fun showChangesToast() {
        StatesRepository.showChangesToast()
    }
}