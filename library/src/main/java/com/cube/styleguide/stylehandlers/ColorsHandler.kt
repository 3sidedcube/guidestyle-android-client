package com.cube.styleguide.stylehandlers

import android.content.Context
import androidx.core.content.ContextCompat
import com.cube.styleguide.utils.Extensions.firstPart
import com.cube.styleguide.utils.Extensions.getPackageNameFlavorAdapted
import java.lang.reflect.Field

class ColorsHandler {
	companion object {
		/**
		 * Returns ordered map of color lists Map<String, List<Pair<String, Int>>>
		 * Example:
		 * The resource as example: <color name="grey_500">#012733</color>
		 * The KEY its the color prefix -> "grey". This grey_500 color will be stored to the map under the key "grey"
		 *
		 * The VALUE its the Pair of:
		 * - first = "grey_500"
		 * - second = ContextCompat.getColor(context, it.colorId) color that will be taken by colorId
		 * */
		fun getColors(context: Context) =
			Class.forName("${context.getPackageNameFlavorAdapted()}.R\$color").declaredFields // returns all declared colors from the colors.xml. Sorting does not match xml file.
				.filter { !it.colorName.startsWith("guidestyle", true) } // exclude the "guidestyle"
				.map { Pair(it.colorName, ContextCompat.getColor(context, it.colorId)) } // creates Pairs with colorName and colorId
				.sortedBy { it.second } // orders given color list from darker to lighter
				.groupBy { it.first.firstPart() } // creates a map where key its string color name, and value its a list of Pairs that are created line above
				.entries.sortedByDescending { it.value.size } // creates descending ordering for the map by number of colors in a given color group
				.associate { it.key to it.value } // it returns created sorted list to the sorted map
				.ifEmpty { null }
	}
}

private val Field.colorId get() = getInt(null)
private val Field.colorName get() = name
