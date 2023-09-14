package com.cube.styleguide.stylehandlers

import android.content.Context
import com.cube.styleguide.utils.Extensions.getPackageNameFlavorAdapted
import java.lang.reflect.Field

class ShadowsHandler {
	companion object {
		fun getShadows(context: Context) =
			Class.forName("${context.getPackageNameFlavorAdapted()}.R\$dimen").declaredFields
				.filter { !it.shadowName.startsWith("guidestyle", true) } // exclude the "guidestyle"
				.filter { it.shadowName.startsWith("elevation", true) } // takes only "elevation"
				.map { Pair(it.shadowName, context.resources.getDimension(it.shadowId)) } // creates Pairs with shadowName and shadowId
				.sortedBy { it.second }// sorts by the float shadow dimension
				.ifEmpty { null }
	}
}

private val Field.shadowId get() = getInt(null)
private val Field.shadowName get() = name
