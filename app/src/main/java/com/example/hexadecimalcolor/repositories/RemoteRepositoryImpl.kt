package com.example.hexadecimalcolor.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.hexadecimalcolor.common.network.WordClient

class RemoteRepositoryImpl(
    private val wordClient: WordClient,
    private val wordPreferences: SharedPreferences,
    private val colorPreferences: SharedPreferences
): RemoteRepository {

    override suspend fun downloadWords(wordsCount: Int): List<String> {
        return wordClient.getWords(wordsCount)
    }

    override fun saveWords(wordList: List<String>) {
        wordPreferences.edit {
            val wordCount = wordList.size

            putInt(WORDS_COUNT_KEY, wordCount)

            wordList.forEachIndexed { index, word ->
                val key = WORD_KEY_PREFIX + index.toString()

                putString(key, word)
            }
        }
    }

    override fun getWords(): List<String> {
        val wordCount = wordPreferences.getInt(WORDS_COUNT_KEY, 0)

        if (wordCount == 0) return emptyList()

        return (0 until wordCount).map {
            val suffix = it.toString()
            wordPreferences.getString(WORD_KEY_PREFIX + suffix, "") ?: ""
        }
    }

    override fun saveColors(colorList: List<String>) {
        colorPreferences.edit {
            val colorCount = colorList.size

            putInt(COLORS_COUNT_KEY, colorCount)

            colorList.forEachIndexed { index, color ->
                val key = COLOR_KEY_PREFIX + index.toString()

                putString(key, color)
            }
        }
    }

    override fun getColors(): List<String> {
        val colorCount = colorPreferences.getInt(COLORS_COUNT_KEY, 0)

        if (colorCount == 0) return emptyList()

        return (0 until colorCount).map {
            val suffix = it.toString()
            colorPreferences.getString(COLOR_KEY_PREFIX + suffix, "") ?: ""
        }
    }

    companion object {
        const val WORDS_COUNT_KEY = "words_count_key"
        const val WORD_KEY_PREFIX = "word_#"

        const val COLORS_COUNT_KEY = "colors_count_key"
        const val COLOR_KEY_PREFIX = "color_#"
    }
}