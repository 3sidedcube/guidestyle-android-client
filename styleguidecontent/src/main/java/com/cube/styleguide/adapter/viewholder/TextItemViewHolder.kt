package com.cube.styleguide.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.databinding.TextItemViewBinding
import com.cube.styleguide.utils.Extensions.secondPart

class TextItemViewHolder(private val binding: TextItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

	fun populate(pair: Pair<String, Int>) {
		binding.apply {
			headerTitle.text = pair.first
			headerTitle.setTextAppearance(pair.second)
		}
	}
}