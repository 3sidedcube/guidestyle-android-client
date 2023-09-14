package com.cube.styleguide.adapter.viewholder

import android.widget.LinearLayout
import com.cube.styleguide.R
import com.cube.styleguide.databinding.SpacingItemViewBinding
import com.cube.styleguide.utils.Extensions.secondPart
import com.cube.styleguide.utils.Extensions.toDP

class SpacingItemViewHolder(binding: SpacingItemViewBinding) : BaseSpacingItemViewHolder<SpacingItemViewBinding>(binding) {
	override fun populate(pair: Pair<String, Int>) {
		val context = itemView.context
		binding.apply {
			headerTitle.text = pair.first.secondPart()
			val dimensions = pair.second
			spacingInDp.text = String.format(context.getString(R.string.guidestyle_dp_text), dimensions.toDP(context))
			view.layoutParams = LinearLayout.LayoutParams(dimensions, dimensions)
		}
	}
}