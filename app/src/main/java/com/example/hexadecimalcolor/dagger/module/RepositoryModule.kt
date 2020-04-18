package com.example.hexadecimalcolor.dagger.module

import android.content.Context
import com.example.hexadecimalcolor.common.COLORS_PREFERENCES_FILE_NAME
import com.example.hexadecimalcolor.common.WORDS_PREFERENCES_FILE_NAME
import com.example.hexadecimalcolor.common.network.WordClient
import com.example.hexadecimalcolor.repositories.RemoteRepositoryImpl
import com.example.hexadecimalcolor.repositories.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideRemoteRepository(wordClient: WordClient): RemoteRepository {
        val wordPreferences = context.getSharedPreferences(WORDS_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val colorPreferences = context.getSharedPreferences(COLORS_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        return RemoteRepositoryImpl(wordClient, wordPreferences, colorPreferences)
    }
}