package com.example.hexadecimalcolor.common.network

import com.example.hexadecimalcolor.common.WORD_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface WordClient {

    @GET(WORD_ENDPOINT)
    suspend fun getWords(@Query("number") wordsCount: Int): List<String>
}