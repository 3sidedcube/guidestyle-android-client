package com.cube.styleguide.stylehandlers

import android.content.Context
import java.lang.reflect.Field

class ScrollEndHandler {
	companion object {
		fun getScrollEnd(context: Context) = Class.forName("${context.packageName}.R\$dimen").declaredFields
			.filter { it.scrollendSpacingName.startsWith("scrollend", true) } // takes only "scrollend"
			.map { Pair(it.scrollendSpacingName, context.resources.getDimensionPixelOffset(it.scrollendSpacingId)) } // creates Pairs with scrollendName and scrollendId
			.sortedBy { it.second } // sorts by the float spacing dimension
			.ifEmpty { null }
	}
}

private val Field.scrollendSpacingId get() = getInt(null)
private val Field.scrollendSpacingName get() = name