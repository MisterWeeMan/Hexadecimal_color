package com.example.hexadecimalcolor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hexadecimalcolor.common.network.WordClient
import com.example.hexadecimalcolor.dagger.component.DaggerMainActivityComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main"
    }

    @Inject
    lateinit var wordClient: WordClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()

        GlobalScope.launch {
            val wordList = wordClient.getWords(5)

            Log.d(TAG, "Number of words in the list: ${wordList.size}")
            Log.d(TAG, "First word in the list: ${wordList[0]}")
        }
    }

    private fun initDagger() {
        DaggerMainActivityComponent.builder()
            .applicationComponent((application as WordApplication).applicationComponent)
            .build()
            .inject(this)
    }
}
