package com.example.hexadecimalcolor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hexadecimalcolor.repositories.RemoteRepository
import javax.inject.Inject

class WordViewModelFactory @Inject constructor(
    private val repository: RemoteRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WordViewModel(repository) as T
    }
}