package com.example.hexadecimalcolor.views

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hexadecimalcolor.R
import kotlinx.android.synthetic.main.item_color.view.*

class ColorAdapter(
    private val colorList: List<Pair<String, String>>
): RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val currentColor = colorList[position]

        holder.bind(currentColor)
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    class ColorViewHolder(
        private val item: View
    ): RecyclerView.ViewHolder(item) {

        fun bind(color: Pair<String, String>) {
            item.apply {
                val newColor = Color.parseColor(color.second)

                tv_color_name.text = color.first
                tv_color.setBackgroundColor(newColor)
            }
        }
    }
}