package com.cube.styleguide.adapter

import android.view.ViewGroup
import com.cube.styleguide.adapter.viewholder.SpacingItemViewHolder
import com.cube.styleguide.databinding.SpacingItemViewBinding
import com.cube.styleguide.utils.Extensions.layoutInflater

class SpacingAdapter(dimens: List<Pair<String, Int>>) : BaseSpacingAdapter<SpacingItemViewHolder, SpacingItemViewBinding>(dimens) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		SpacingItemViewHolder(SpacingItemViewBinding.inflate(parent.layoutInflater, parent, false))
}