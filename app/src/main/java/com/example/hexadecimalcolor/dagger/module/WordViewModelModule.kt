package com.example.hexadecimalcolor.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.example.hexadecimalcolor.MainActivity
import com.example.hexadecimalcolor.dagger.scope.MainActivityScope
import com.example.hexadecimalcolor.viewmodels.WordViewModel
import com.example.hexadecimalcolor.viewmodels.WordViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class WordViewModelModule(
    private val activity: MainActivity
) {

    @Provides
    @MainActivityScope
    fun provideWordViewModel(viewModelFactory: WordViewModelFactory): WordViewModel {
        return ViewModelProvider(activity, viewModelFactory).get(WordViewModel::class.java)
    }
}