package com.cube.styleguide.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseSpacingItemViewHolder<V : ViewBinding>(protected val binding: V) : RecyclerView.ViewHolder(binding.root) {
	abstract fun populate(pair: Pair<String, Int>)
}