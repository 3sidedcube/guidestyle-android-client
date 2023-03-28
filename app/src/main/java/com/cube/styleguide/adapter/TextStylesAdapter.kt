package com.cube.styleguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.adapter.viewholder.TextItemViewHolder
import com.cube.styleguide.databinding.TextItemViewBinding

class TextStylesAdapter(private val dimens: List<Pair<String, Int>>) : RecyclerView.Adapter<TextItemViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
		val binding = TextItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return TextItemViewHolder(binding)
	}

	override fun getItemCount() = dimens.size

	override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {

		holder.populate(dimens[position])
	}
}