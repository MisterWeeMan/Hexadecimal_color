package com.example.hexadecimalcolor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hexadecimalcolor.dagger.component.DaggerMainActivityComponent
import com.example.hexadecimalcolor.repositories.RemoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private val randomObj = Random(System.currentTimeMillis())

        const val MAX_HEX_NUMBER = 0xffffff + 1
        const val STRING_FORMAT = "#%06x"
        const val TAG = "Main"
    }

    @Inject
    lateinit var repository: RemoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()

        val colorCount = 5

        GlobalScope.launch {
            val wordList = repository.downloadWords(colorCount)
            val colorList = (0 until colorCount).map { generateRandomHex() }

            Log.d(TAG, "Word list: $wordList")
            Log.d(TAG, "Color list: $colorList")

            repository.saveWords(wordList)
            repository.saveColors(colorList)

            val wordListPreferences = repository.getWords()
            val colorListPreferences = repository.getColors()

            Log.d(TAG, "Word list preferences: $wordListPreferences")
            Log.d(TAG, "Color list preferences: $colorListPreferences")
        }
    }

    private fun initDagger() {
        DaggerMainActivityComponent.builder()
            .applicationComponent((application as WordApplication).applicationComponent)
            .build()
            .inject(this)
    }

    private fun generateRandomHex(): String {
        val randomNumber = randomObj.nextInt(MAX_HEX_NUMBER)

        val colorCode = String.format(STRING_FORMAT, randomNumber)

        return colorCode.toUpperCase(Locale.getDefault())
    }
}
