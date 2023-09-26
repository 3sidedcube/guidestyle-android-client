package com.cube.styleguide.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cube.styleguide.R
import java.util.Locale

object Extensions {

	fun String.lastPart(divider: String) = split(divider).lastOrNull() ?: this

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

	/**
	 * Tries to recover the package name of the app.
	 * In case the app has multiple flavours but they do not have different res(colors,dimens, style) it will fall back on the main flavour (aka production flavour)
	 *
	 * @return package name or empty string
	 */
	fun Context.getPackageNameFlavorAdapted(): String = try {
		Class.forName("$packageName.R\$style").declaredFields
		packageName
	} catch (cne: ClassNotFoundException) {
		val staging = ".staging"
		val test = ".test"
		val dev = ".dev"

		if (packageName.endsWith(staging, true)) {
			packageName.replace(staging, "")
		} else if (packageName.endsWith(test, true)) {
			packageName.replace(test, "")
		} else if (packageName.endsWith(dev, true)) {
			packageName.replace(dev, "")
		} else {
			""
		}
	}

	fun Context.getColor(colorId: Int) = ContextCompat.getColor(this, colorId)

	val ViewGroup.layoutInflater get() = LayoutInflater.from(this.context)

	val Context.layoutInflater get() = LayoutInflater.from(this)

	fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}