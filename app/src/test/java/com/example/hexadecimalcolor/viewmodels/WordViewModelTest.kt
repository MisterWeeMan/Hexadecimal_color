package com.example.hexadecimalcolor.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.hexadecimalcolor.common.utils.LoadingState
import com.example.hexadecimalcolor.repositories.RemoteRepository
import com.example.hexadecimalcolor.viewmodels.utils.MainCoroutinesRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WordViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @MockK
    private lateinit var repository: RemoteRepository

    private lateinit var viewModel: WordViewModel

    @SpyK
    var loadingStateObserver = Observer<LoadingState> {  }

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = spyk(WordViewModel(repository))

        viewModel.loadingState.observeForever(loadingStateObserver)
    }

    @Test
    fun `loadColors successful call correctly change the value of loading state to success`() {
        // === Setup ===
        val words = (1..10).map { "Word$it" }
        val colors = (1..10).map { "Color$it" }
        val map = words.mapIndexed { index, word ->
            word to colors[index]
        }.toMap()
        val loadingStateExpected = LoadingState.Success(map)

        coEvery { repository.getWords() } returns words
        coEvery { repository.getColors() } returns colors

        // === Call ===
        runBlocking { viewModel.loadColors() }

        // === Assertions ===
        verify { loadingStateObserver.onChanged(loadingStateExpected) }
        assertEquals(loadingStateExpected, viewModel.loadingState.value)
    }

    @Test
    fun `loadColors unsuccessful call correctly change the value of loading state to error`() {
        // === Setup ===
        val errorMessage = "error"
        val loadingStateExpected = LoadingState.Failure(errorMessage)
        coEvery { repository.getWords() } throws RuntimeException(errorMessage)

        // === Call ===
        runBlocking { viewModel.loadColors() }

        // === Assertions ===
        verify { loadingStateObserver.onChanged(loadingStateExpected) }
        assertEquals(loadingStateExpected, viewModel.loadingState.value)
    }

    @Test
    fun `fetchColors successful call correctly change the value of loading state to success`() {
        // === Setup ===
        val words = (1..10).map { "Word$it" }
        val color = "#34FF48"
        val map = words.mapIndexed { _, word ->
            word to color
        }.toMap()
        val loadingStateExpected = LoadingState.Success(map)

        coEvery { repository.downloadWords(any()) } returns words
        every { viewModel.generateRandomHex() } returns color
        coEvery { repository.saveWords(any()) } just runs
        coEvery { repository.saveColors(any()) } just runs

        // === Call ===
        runBlocking { viewModel.fetchColors() }

        // === Assertions ===
        verify { loadingStateObserver.onChanged(loadingStateExpected) }
        assertEquals(loadingStateExpected, viewModel.loadingState.value)
    }

    @Test
    fun `fetchColors unsuccessful call correctly change the value of loading state to error`() {
        // === Setup ===
        val errorMessage = "error"
        val loadingStateExpected = LoadingState.Failure(errorMessage)
        coEvery { repository.downloadWords(any()) } throws RuntimeException(errorMessage)

        // === Call ===
        runBlocking { viewModel.fetchColors() }

        // === Assertions ===
        verify { loadingStateObserver.onChanged(loadingStateExpected) }
        assertEquals(loadingStateExpected, viewModel.loadingState.value)
    }
}