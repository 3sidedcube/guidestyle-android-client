package com.cube.styleguide.stylehandlers

import android.content.Context

class TextStylesHandler {
	companion object {
		private const val textStyleAttribute = android.R.attr.textStyle
		private const val textSizeAttribute = android.R.attr.textSize
		fun getTextStyles(
			context: Context,
			prefixesList: List<String> = listOf("Body", "Heading", "Caption", "Subtitle", "Bold", "Regular")
		) = Class.forName("${context.packageName}.R\$style").declaredFields.let { themes ->
			themes.mapNotNull { theme ->
				if (prefixesList.any { theme.name.startsWith(it) }) Pair(theme.name, theme.getInt(null)) else null

				// sorts by the android.R.attr.textStyle attribute, then descending by the android.R.attr.textSize attribute and ascending by style name
			}.sortedWith(compareByDescending<Pair<String, Int>> {
				val themeTypedArray = context.obtainStyledAttributes(it.second, intArrayOf(textStyleAttribute))
				val textStyle = themeTypedArray.getString(0)
				themeTypedArray.recycle()
				textStyle
			}.thenByDescending {
				val themeTypedArray = context.obtainStyledAttributes(it.second, intArrayOf(textSizeAttribute))
				val textSize = themeTypedArray.getDimensionPixelSize(0, 0)
				themeTypedArray.recycle()
				textSize
			}.thenBy {
				it.first
			}).ifEmpty { return@let null }
		}
	}
}