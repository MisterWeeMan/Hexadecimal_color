package com.example.hexadecimalcolor.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hexadecimalcolor.dagger.component.DaggerMainActivityComponent
import com.example.hexadecimalcolor.dagger.module.WordViewModelModule
import com.example.hexadecimalcolor.viewmodels.WordViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hexadecimalcolor.R
import com.example.hexadecimalcolor.WordApplication
import com.example.hexadecimalcolor.common.utils.LoadingState
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()
        initObservableData()
        initLayout()

        viewModel.loadColors()
    }

    private fun initLayout() {
        btn_generate.setOnClickListener {
            viewModel.fetchColors()
        }
    }

    private fun initObservableData() {
        viewModel.loadingState.observe(this, Observer {
            when (it) {
                is LoadingState.Loading -> {
                    hideData()
                    showLoading()
                }
                is LoadingState.Success -> {
                    initRecyclerView(it.data.toList())
                    hideLoading()
                    showData()
                }
                is LoadingState.Failure -> {
                    hideLoading()
                    showData()
                    showError(it.error)
                }
            }
        })
    }

    private fun showLoading() {
        pb_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pb_loading.visibility = View.GONE
    }

    private fun showData() {
        rv_colors.visibility = View.VISIBLE
        btn_generate.visibility = View.VISIBLE
    }

    private fun hideData() {
        rv_colors.visibility = View.GONE
        btn_generate.visibility = View.GONE
    }

    private fun showError(error: String?) {
        val message = error ?: getString(R.string.something_went_wrong)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun initRecyclerView(listColor: List<Pair<String, String>>) {
        rv_colors.apply {
            adapter = ColorAdapter(listColor)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initDagger() {
        DaggerMainActivityComponent.builder()
            .applicationComponent((application as WordApplication).applicationComponent)
            .wordViewModelModule(WordViewModelModule(this))
            .build()
            .inject(this)
    }
}
