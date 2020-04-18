package com.example.hexadecimalcolor.repositories

interface RemoteRepository {

    suspend fun downloadWords(wordsCount: Int): List<String>

    fun saveWords(wordList: List<String>)

    fun getWords(): List<String>

    fun saveColors(colorList: List<String>)

    fun getColors(): List<String>
}