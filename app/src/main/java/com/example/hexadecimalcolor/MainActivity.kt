package com.example.hexadecimalcolor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hexadecimalcolor.dagger.component.DaggerMainActivityComponent
import com.example.hexadecimalcolor.dagger.module.WordViewModelModule
import com.example.hexadecimalcolor.viewmodels.WordViewModel
import androidx.lifecycle.Observer
import com.example.hexadecimalcolor.common.utils.LoadingState
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main"
    }

    @Inject
    lateinit var viewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()
        initObservableData()
        initLayout()

        Log.d(TAG, "loadColors called")
        viewModel.loadColors()
    }

    private fun initLayout() {
        btn_generate.setOnClickListener {
            Log.d(TAG, "fetchColors called")
            viewModel.fetchColors()
        }
    }

    private fun initObservableData() {
        viewModel.loadingState.observe(this, Observer {
            when (it) {
                is LoadingState.Loading -> Log.d(TAG, "Loading...")
                is LoadingState.Success -> {
                    val dataEmpty = it.data.isEmpty()
                    Log.d(TAG, "Map is empty: $dataEmpty")

                    if (!dataEmpty) {
                        Log.d(TAG, "Map elements: ${it.data}")
                    }
                }
                is LoadingState.Failure -> Log.e(TAG, "Error message: ${it.error ?: "message is null"}")
            }
        })
    }

    private fun initDagger() {
        DaggerMainActivityComponent.builder()
            .applicationComponent((application as WordApplication).applicationComponent)
            .wordViewModelModule(WordViewModelModule(this))
            .build()
            .inject(this)
    }
}
