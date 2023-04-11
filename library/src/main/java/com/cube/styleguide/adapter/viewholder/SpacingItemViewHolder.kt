package com.cube.styleguide.adapter.viewholder

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.R
import com.cube.styleguide.databinding.SpacingItemViewBinding
import com.cube.styleguide.utils.Extensions.secondPart
import com.cube.styleguide.utils.Extensions.toDP

class SpacingItemViewHolder(private val binding: SpacingItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
	fun populate(pair: Pair<String, Int>) {
		val context = itemView.context
		binding.apply {
			headerTitle.text = pair.first.secondPart()
			val dimensions = itemView.context.resources.getDimensionPixelOffset(pair.second)
			spacingInDp.text = String.format(context.getString(R.string.guidestyle_dp_text), dimensions.toDP(context))
			view.layoutParams = LinearLayout.LayoutParams(dimensions, dimensions)
		}
	}
}