package com.cube.styleguide.adapter

import android.view.ViewGroup
import com.cube.styleguide.adapter.viewholder.HorizontalSpacingViewHolder
import com.cube.styleguide.databinding.ItemHirizontalSpacingViewBinding
import com.cube.styleguide.utils.Extensions.layoutInflater

class HorizontalSpacingAdapter(dimens: List<Pair<String, Int>>) : BaseSpacingAdapter<HorizontalSpacingViewHolder, ItemHirizontalSpacingViewBinding>(dimens) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		HorizontalSpacingViewHolder(ItemHirizontalSpacingViewBinding.inflate(parent.layoutInflater, parent, false))
}