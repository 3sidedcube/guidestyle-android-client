package com.cube.styleguide.adapter.viewholder

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.cube.styleguide.R
import com.cube.styleguide.databinding.ItemHirizontalSpacingViewBinding
import com.cube.styleguide.utils.Extensions.capitalize
import com.cube.styleguide.utils.Extensions.firstPart
import com.cube.styleguide.utils.Extensions.secondPart
import com.cube.styleguide.utils.Extensions.toDP

class HorizontalSpacingViewHolder(binding: ItemHirizontalSpacingViewBinding) : BaseSpacingItemViewHolder<ItemHirizontalSpacingViewBinding>(binding) {
	override fun populate(pair: Pair<String, Int>) {
		val context = itemView.context
		binding.apply {
			val dimensions = pair.second
			val titleText = "${pair.first.firstPart().capitalize()} ${pair.first.secondPart().capitalize()}\n${String.format(context.getString(R.string.guidestyle_dp_text), dimensions.toDP(context))}"
			title.text = titleText
			viewLeft.layoutParams = ConstraintLayout.LayoutParams(dimensions, MATCH_PARENT)
			viewRight.layoutParams = ConstraintLayout.LayoutParams(dimensions, MATCH_PARENT)

			val constraintSet = ConstraintSet()
			constraintSet.clone(binding.constraint)
			constraintSet.connect(binding.viewLeft.id, ConstraintSet.START, binding.constraint.id, ConstraintSet.START, 0)
			constraintSet.connect(binding.viewRight.id, ConstraintSet.END, binding.constraint.id, ConstraintSet.END, 0)
			constraintSet.applyTo(binding.constraint)
		}
	}
}