package com.cube.styleguide.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.R
import com.cube.styleguide.databinding.TextItemViewBinding

class TextItemViewHolder(private val binding: TextItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
	fun populate(pair: Pair<String, Int>) {
		val context = itemView.context
		binding.apply {
			sectionLabel.text = pair.first
			headerTitle.text = context.getString(R.string.guidestyle_textstyle_name)
			headerTitle.setTextAppearance(pair.second)
		}
	}
}