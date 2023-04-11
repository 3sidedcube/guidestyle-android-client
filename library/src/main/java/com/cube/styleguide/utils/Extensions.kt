package com.cube.styleguide.utils

import android.content.Context

object Extensions {
	fun String.firstPart(): String {
		val content = split("_")
		return if (content.size > 1) {
			content[0]
		} else {
			this
		}
	}

	fun String.secondPart(): String {
		val content = split("_")
		return if (content.size > 1) {
			content[1]
		} else {
			this
		}
	}

	fun Int.toDP(context: Context): Int {
		return (this / context.resources.displayMetrics.density).toInt()
	}
}