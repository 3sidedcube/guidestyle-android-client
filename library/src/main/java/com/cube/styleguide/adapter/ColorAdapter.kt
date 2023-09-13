package com.cube.styleguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.adapter.viewholder.ColorItemViewHolder
import com.cube.styleguide.databinding.ColorBannerViewBinding

class ColorAdapter(private val colors: Map<String, List<Pair<String, Int>>>) : RecyclerView.Adapter<ColorItemViewHolder>() {
	private var keys = colors.keys.toList()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
		val binding = ColorBannerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ColorItemViewHolder(binding)
	}

	override fun getItemCount() = colors.size

	override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {

		holder.populate(colors[keys[position]])
	}

}