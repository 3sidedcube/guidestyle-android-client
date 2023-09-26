package com.cube.styleguide.stylehandlers

import android.content.Context
import com.cube.styleguide.utils.Extensions.lastPart
import java.lang.reflect.Field

class ButtonsHandler {
	companion object {

		private const val UNDEFINED_BTN_POSITION = 999
		fun getButtonsStyles(context: Context) = Class.forName("${context.packageName}.R\$style").declaredFields
			.filter { it.buttonName.lowercase().startsWith("button") }
			.map { theme -> Pair(theme.name.lastPart("_"), theme.buttonId) }
			.sortedBy { button ->
				ButtonOrdering.values().toList().firstOrNull { orderItem -> button.first.lowercase().contains(orderItem.name.lowercase()) }?.position ?: UNDEFINED_BTN_POSITION
			}.ifEmpty { null }
	}
}

private enum class ButtonOrdering(val position: Int) {
	Primary(0),
	Secondary(1),
	Fab(2),
	Tertiary(3),
	Sticky(4)
}

private val Field.buttonId get() = getInt(null)
private val Field.buttonName get() = name