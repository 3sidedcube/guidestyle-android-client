package com.cube.styleguide.adapter.viewholder

import com.cube.styleguide.R
import com.cube.styleguide.databinding.ItemRadiusBinding
import com.cube.styleguide.utils.Extensions.capitalize
import com.cube.styleguide.utils.Extensions.secondPart
import com.cube.styleguide.utils.Extensions.toDP

class RadiusItemViewHolder(binding: ItemRadiusBinding) : BaseSpacingItemViewHolder<ItemRadiusBinding>(binding) {
	override fun populate(pair: Pair<String, Int>) {
		val context = itemView.context
		binding.apply {
			headerTitle.text = pair.first.secondPart().capitalize()
			val dimensions = pair.second
			spacingInDp.text = String.format(context.getString(R.string.guidestyle_dp_text), dimensions.toDP(context))
			val shapeAppearanceModel = binding.view.shapeAppearanceModel.toBuilder()
				.setAllCornerSizes(dimensions.toFloat())
				.build()
			binding.view.shapeAppearanceModel = shapeAppearanceModel
		}
	}
}