package com.example.hexadecimalcolor.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hexadecimalcolor.common.WORDS_DOWNLOADED
import com.example.hexadecimalcolor.common.utils.LoadingState
import com.example.hexadecimalcolor.repositories.RemoteRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class WordViewModel(
    private val repository: RemoteRepository
) : ViewModel() {

    val loadingState: LiveData<LoadingState>
        get() = mLoadingState
    private val mLoadingState = MutableLiveData<LoadingState>()

    fun loadColors() {
        mLoadingState.value = LoadingState.Loading

        try {
            val words = repository.getWords()
            val color = repository.getColors()

            Log.d("ViewModel", "Words count ${words.size}")
            Log.d("ViewModel", "Colors count ${color.size}")

            if (words.isEmpty() || color.isEmpty()) {
                mLoadingState.value = LoadingState.Success(mapOf())
            } else {
                mLoadingState.value = LoadingState.Success(
                    words.mapIndexed { index, word ->
                        word to color[index]
                    }.toMap()
                )
            }
        } catch (exc: Exception) {
            mLoadingState.value = LoadingState.Failure(exc.message)
        }
    }

    fun fetchColors() {
        mLoadingState.value = LoadingState.Loading

        viewModelScope.launch {
            try {
                val words = repository.downloadWords(WORDS_DOWNLOADED)
                val colors = words.indices.map { generateRandomHex() }

                mLoadingState.value = LoadingState.Success(
                    words.mapIndexed { index, word ->
                        word to colors[index]
                    }.toMap()
                )

                repository.saveWords(words)
                repository.saveColors(colors)
            } catch (exc: Exception) {
                mLoadingState.value = LoadingState.Failure(exc.message)
            }
        }
    }

    private fun generateRandomHex(): String {
        val randomNumber = randomObj.nextInt(MAX_HEX_NUMBER)

        val colorCode = String.format(STRING_FORMAT, randomNumber)

        return colorCode.toUpperCase(Locale.getDefault())
    }

    companion object {
        private val randomObj by lazy { Random(System.currentTimeMillis()) }

        const val MAX_HEX_NUMBER = 0xffffff + 1
        const val STRING_FORMAT = "#%06x"
    }
}