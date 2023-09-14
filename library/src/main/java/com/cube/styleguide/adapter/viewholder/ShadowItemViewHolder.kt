package com.cube.styleguide.adapter.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.databinding.ShadowItemViewBinding
import com.cube.styleguide.utils.Extensions.capitalize
import com.cube.styleguide.utils.Extensions.secondPart

class ShadowItemViewHolder(private val binding: ShadowItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
	@SuppressLint("SetTextI18n")
	fun populate(shadow: Pair<String, Float>) {
		binding.label.text = "Shadow - ${shadow.first.secondPart().capitalize()}"
		binding.view.elevation = shadow.second
	}
}