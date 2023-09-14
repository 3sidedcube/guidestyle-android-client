package com.cube.styleguide.stylehandlers

import android.content.Context
import java.lang.reflect.Field

class HorizontalSpacingHandler {
	companion object {
		fun getHorizontalSpacings(context: Context) = Class.forName("${context.packageName}.R\$dimen").declaredFields
			.filter { it.horizontalSpacingName.startsWith("horizontal", true) } // takes only "horizontal"
			.map { Pair(it.horizontalSpacingName, context.resources.getDimensionPixelOffset(it.horizontalSpacingId)) } // creates Pairs with spacingName and spacingId
			.sortedBy { it.second } // sorts by the float spacing dimension
			.ifEmpty { null }
	}
}

private val Field.horizontalSpacingId get() = getInt(null)
private val Field.horizontalSpacingName get() = name