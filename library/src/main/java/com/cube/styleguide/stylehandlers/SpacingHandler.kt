package com.cube.styleguide.stylehandlers

import android.content.Context
import java.lang.reflect.Field

class SpacingHandler {
	companion object {
		fun getSpacings(context: Context) = Class.forName("${context.packageName}.R\$dimen").declaredFields
			.filter { it.spacingName.startsWith("spacing", true) } // takes only "spacing"
			.map { Pair(it.spacingName, context.resources.getDimensionPixelOffset(it.spacingId)) } // creates Pairs with spacingName and spacingId
			.sortedBy { it.second }// sorts by the float spacing dimension
			.ifEmpty { null }
	}
}

private val Field.spacingId get() = getInt(null)
private val Field.spacingName get() = name