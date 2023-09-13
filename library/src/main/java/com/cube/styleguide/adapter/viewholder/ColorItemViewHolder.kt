package com.cube.styleguide.adapter.viewholder

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.R
import com.cube.styleguide.databinding.ColorBannerViewBinding
import com.cube.styleguide.databinding.ColorItemViewBinding
import com.cube.styleguide.utils.Extensions.secondPart

class ColorItemViewHolder(private val binding: ColorBannerViewBinding) : RecyclerView.ViewHolder(binding.root) {
	fun populate(list: List<Pair<String, Int>>?) {
        binding.colorContainer.removeAllViews()
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        list?.forEachIndexed { index, pair ->
            val colorItemView = ColorItemViewBinding.inflate(LayoutInflater.from(itemView.context))
            colorItemView.root.layoutParams = params

			// If the colour list size is even it won't have a middle position, so instead we will take the index that is half of the size.
			val isMiddle = (list.size / 2) - (if (list.size % 2 == 0) 1 else 0)
            populateView(colorItemView, isMiddle == index, pair)
            binding.colorContainer.addView(colorItemView.root)
        }
    }

    private fun populateView(colorItemView: ColorItemViewBinding, isMiddle: Boolean, colorPair: Pair<String, Int>) {
        val context = itemView.context
        colorItemView.apply {
            colorName.text = colorPair.first.secondPart()
			colorName.isVisible = colorPair.first.secondPart() != colorPair.first

			val backgroundColor = context.getColor(if (isMiddle) R.color.guidestyle_black else R.color.guidestyle_white)
			val textColor = context.getColor(if (isMiddle) R.color.guidestyle_white else R.color.guidestyle_black)

			headerTitle.setBackgroundColor(backgroundColor)
			headerTitle.setTextColor(textColor)
			colorName.setBackgroundColor(backgroundColor)
			colorName.setTextColor(textColor)

            if (isMiddle) {
                val name = colorPair.first.split("_")
				headerTitle.text = name[0]
            }

			color.setBackgroundColor(colorPair.second)
			color.text = Integer.toHexString(colorPair.second)
        }
    }
}