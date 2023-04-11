package ru.shiryaev.data.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomFactory(
        private val vm: ViewModel
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm as T
    }
}