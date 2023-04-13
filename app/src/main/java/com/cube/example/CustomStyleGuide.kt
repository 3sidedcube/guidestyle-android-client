package com.cube.example

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.cube.styleguide.StyleGuideFragment

/**
 * Extend the StyleGuideFragment so you can add specific custom views of your project
 */
class CustomStyleGuide : StyleGuideFragment() {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		/**
		 * call the super if you want to have the default StyleGuide for the main views (etc Colors, Buttons, TextStyles, RadioButton,CheckBoxes, Switches )
		 * or remove it if you only want your custom views
		 */

		super.onViewCreated(view, savedInstanceState)
		val text = TextView(context)
		text.text = "test"
		addCustomView(text)

		val text2 = TextView(context)
		text2.text = "test2"
		addCustomView(text2)
	}
}