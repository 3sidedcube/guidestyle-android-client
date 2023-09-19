package com.cube.styleguide.stylehandlers

import android.content.Context
import java.lang.reflect.Field

class RadiusHandler {
	companion object {
		fun getRadius(context: Context) = Class.forName("${context.packageName}.R\$dimen").declaredFields
			.filter { it.radiusSpacingName.startsWith("radius", true) } // takes only "radius"
			.map { Pair(it.radiusSpacingName, context.resources.getDimensionPixelOffset(it.radiusSpacingId)) } // creates Pairs with radiusName and radiusId
			.sortedBy { it.second }// sorts by the float spacing dimension
			.ifEmpty { null }
	}
}

private val Field.radiusSpacingId get() = getInt(null)
private val Field.radiusSpacingName get() = name