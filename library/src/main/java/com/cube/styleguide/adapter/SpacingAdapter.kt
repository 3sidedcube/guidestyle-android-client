package com.cube.styleguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.adapter.viewholder.SpacingItemViewHolder
import com.cube.styleguide.databinding.SpacingItemViewBinding

class SpacingAdapter(private val dimens: List<Pair<String, Int>>) : RecyclerView.Adapter<SpacingItemViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacingItemViewHolder {
		val binding = SpacingItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SpacingItemViewHolder(binding)
	}

	override fun getItemCount() = dimens.size

	override fun onBindViewHolder(holder: SpacingItemViewHolder, position: Int) {

		holder.populate(dimens[position])
	}
}