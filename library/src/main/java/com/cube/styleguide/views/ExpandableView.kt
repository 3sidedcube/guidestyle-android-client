package com.cube.styleguide.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import com.cube.styleguide.R
import com.cube.styleguide.databinding.ExpandableViewBinding

class ExpandableView constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

	private val binding = ExpandableViewBinding.inflate(LayoutInflater.from(context), this, true)
	private var expanded: Boolean = false

	private fun updateViewsVisibility(value: Boolean) {
		children.forEachIndexed { index, view ->
			if (index != 0) {
				view.visibility = if (value) View.VISIBLE else View.GONE
			}
		}
	}

	init {
		context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableView, 0, 0).apply {
			binding.header.text = getString(R.styleable.ExpandableView_header)
			expanded = getBoolean(R.styleable.ExpandableView_expanded, false)
		}
		binding.apply {
			container.setOnClickListener {
				val drawable = if (expanded) {
					context.getDrawable(R.drawable.ic_chevron_down)
				} else {
					context.getDrawable(R.drawable.ic_chevron_up)
				}
				expanded = !expanded
				updateViewsVisibility(expanded)
				header.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
			}
		}
	}

	override fun onFinishInflate() {
		super.onFinishInflate()
		updateViewsVisibility(expanded)
	}
}