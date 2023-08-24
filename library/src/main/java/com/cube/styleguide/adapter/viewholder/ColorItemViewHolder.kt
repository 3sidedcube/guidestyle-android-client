package com.cube.styleguide.adapter.viewholder

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.R
import com.cube.styleguide.databinding.ColorBannerViewBinding
import com.cube.styleguide.databinding.ColorItemViewBinding
import com.cube.styleguide.utils.Extensions.secondPart

class ColorItemViewHolder(private val binding: ColorBannerViewBinding) : RecyclerView.ViewHolder(binding.root) {

	private fun getMiddlePosition(colors: List<Pair<String, Int>>?): Int {
		return if (colors?.size?.mod(2) == 1) {
			colors.size.div(2f).toInt()
		} else {
			-1
		}
	}

	fun populate(list: List<Pair<String, Int>>?) {
		binding.colorContainer.removeAllViews()
		val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
		list?.forEachIndexed { index, pair ->
			val colorItemView = ColorItemViewBinding.inflate(LayoutInflater.from(itemView.context))
			colorItemView.root.layoutParams = params
            // If the colour list size is even it won't have a middle position, so instead we will take the index that is half of the size.
            val isMiddle = if (list.size%2 == 0) list.size/2 else getMiddlePosition(list)
			populateView(colorItemView, isMiddle == index , pair)
			binding.colorContainer.addView(colorItemView.root)
		}
	}

	private fun populateView(colorItemView: ColorItemViewBinding, isMiddle: Boolean, colorPair: Pair<String, Int>) {
		val context = itemView.context
		colorItemView.apply {
			colorName.text = colorPair.first.secondPart()

			if (isMiddle) {
				headerTitle.setBackgroundColor(ContextCompat.getColor(context, R.color.guidestyle_black))
				headerTitle.setTextColor(ContextCompat.getColor(context, R.color.guidestyle_white))
				colorName.setBackgroundColor(ContextCompat.getColor(context, R.color.guidestyle_black))
				colorName.setTextColor(ContextCompat.getColor(context, R.color.guidestyle_white))
				val name = colorPair.first.split("_")
				if (name.size > 1) {
					headerTitle.text = name[0]
				}
			} else {
				headerTitle.setBackgroundColor(ContextCompat.getColor(context, R.color.guidestyle_white))
				headerTitle.setTextColor(ContextCompat.getColor(context, R.color.guidestyle_black))
				colorName.setBackgroundColor(ContextCompat.getColor(context, R.color.guidestyle_white))
				colorName.setTextColor(ContextCompat.getColor(context, R.color.guidestyle_black))
			}
			color.setBackgroundColor(ContextCompat.getColor(context, colorPair.second))
			color.text = Integer.toHexString(ContextCompat.getColor(context, colorPair.second))
		}
	}
}