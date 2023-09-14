package com.cube.styleguide.adapter

import android.view.ViewGroup
import com.cube.styleguide.adapter.viewholder.RadiusItemViewHolder
import com.cube.styleguide.databinding.ItemRadiusBinding
import com.cube.styleguide.utils.Extensions.layoutInflater

class RadiusAdapter(dimens: List<Pair<String, Int>>) : BaseSpacingAdapter<RadiusItemViewHolder, ItemRadiusBinding>(dimens) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		RadiusItemViewHolder(ItemRadiusBinding.inflate(parent.layoutInflater, parent, false))
}