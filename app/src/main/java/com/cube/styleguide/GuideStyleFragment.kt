package com.cube.styleguide

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import com.cube.styleguide.adapter.ColorAdapter
import com.cube.styleguide.adapter.SpacingAdapter
import com.cube.styleguide.adapter.TextStylesAdapter
import com.cube.styleguide.databinding.FragmentStyleguideBinding
import com.cube.styleguide.fragments.BottomSheetFragment
import com.cube.styleguide.utils.Extensions.firstPart
import com.cube.styleguide.utils.ShakeSensorListener
import java.lang.reflect.Field

class GuideStyleFragment : BottomSheetFragment(R.layout.fragment_styleguide) {
	private var binding: FragmentStyleguideBinding? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding = FragmentStyleguideBinding.bind(view)

		populateColors()

		populateSpacings()

		populateStyles()

		binding?.closeButton?.setOnClickListener { dismiss() }
	}

	private fun populateStyles() {
		var buttonStylesList = listOf<Pair<String, Int>>()
		var textStylesList = listOf<Pair<String, Int>>()
		var checkBoxStylesList = listOf<Pair<String, Int>>()
		var radioButtonStylesList = listOf<Pair<String, Int>>()
		var switchButtonStylesList = listOf<Pair<String, Int>>()

		val themes: Array<Field> = Class.forName(context?.packageName + ".R\$style").declaredFields
		for (theme in themes) {
			val themeName: String = theme.name
			val themeId: Int = theme.getInt(null)
			if (themeName.startsWith("Button_")) {
				buttonStylesList = buttonStylesList.plus(Pair(themeName, themeId))
			} else if (themeName.startsWith("Checkbox")) {
				checkBoxStylesList = checkBoxStylesList.plus(Pair(themeName, themeId))
			} else if (themeName.startsWith("Switch")) {
				switchButtonStylesList = switchButtonStylesList.plus(Pair(themeName, themeId))
			} else if (themeName.startsWith("RadioButton")) {
				radioButtonStylesList = radioButtonStylesList.plus(Pair(themeName, themeId))
			} else if (themeName.startsWith("Heading_") || themeName.startsWith("Body_") || themeName.startsWith("Caption_") || themeName.startsWith("Subtitle_")) {
				textStylesList = textStylesList.plus(Pair(themeName, themeId))
			}
		}
		binding?.apply {
			val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
			val bottom = resources.getDimensionPixelSize(R.dimen.spacing_20)
			val vertical = resources.getDimensionPixelSize(R.dimen.spacing_2)
			params.setMargins(vertical, 0, vertical, bottom)
			if (textStylesList.isEmpty()) {
				textContainerView.visibility = View.GONE
			} else {
				textRecyclerView.adapter = TextStylesAdapter(textStylesList)
			}
			if (buttonStylesList.isEmpty()) {
				buttonContainerView.visibility = View.GONE
			} else {
				buttonEnabledContainerView.removeAllViews()
				buttonDisabledContainerView.removeAllViews()
				buttonStylesList.forEach {
					val button = AppCompatButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					button.text = it.first
					button.layoutParams = params
					button.isClickable = true
					val disabledbutton = AppCompatButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledbutton.text = it.first
					disabledbutton.isEnabled = false
					disabledbutton.layoutParams = params
					buttonEnabledContainerView.addView(button)
					buttonDisabledContainerView.addView(disabledbutton)
				}
			}
			if (checkBoxStylesList.isEmpty()) {
				checkboxContainerView.visibility = View.GONE
			} else {
				checkboxContainerView.removeAllViews()
				checkboxDisabledContainerView.removeAllViews()
				checkBoxStylesList.forEach {
					val checkbox = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					checkbox.text = it.first
					checkbox.isClickable = true
					checkbox.layoutParams = params
					val disabledCheckbox = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledCheckbox.isEnabled = false
					disabledCheckbox.text = it.first
					disabledCheckbox.layoutParams = params

					val checkboxChecked = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					checkboxChecked.text = it.first
					checkboxChecked.isClickable = true
					checkboxChecked.isChecked = true
					checkboxChecked.layoutParams = params
					val disabledCheckedCheckbox = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledCheckedCheckbox.isChecked = true
					disabledCheckedCheckbox.isEnabled = false
					disabledCheckedCheckbox.text = it.first
					disabledCheckedCheckbox.layoutParams = params

					checkboxEnabledContainerView.addView(checkbox)
					checkboxEnabledContainerView.addView(checkboxChecked)
					checkboxDisabledContainerView.addView(disabledCheckbox)
					checkboxDisabledContainerView.addView(disabledCheckedCheckbox)
				}
			}

			if (radioButtonStylesList.isEmpty()) {
				radiobuttonContainerView.visibility = View.GONE
			} else {
				radiobuttonEnabledContainerView.removeAllViews()
				radiobuttonDisabledContainerView.removeAllViews()
				radioButtonStylesList.forEach {
					val radioButton = AppCompatRadioButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					radioButton.text = it.first
					radioButton.isClickable = true
					radioButton.layoutParams = params
					val disabledRadioButton = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledRadioButton.isEnabled = false
					disabledRadioButton.text = it.first
					disabledRadioButton.layoutParams = params

					val radioSelectedButton = AppCompatRadioButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					radioSelectedButton.text = it.first
					radioSelectedButton.isClickable = true
					radioSelectedButton.isChecked = true
					radioSelectedButton.layoutParams = params
					val disabledSelectedRadioButton = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledSelectedRadioButton.isEnabled = false
					disabledSelectedRadioButton.isChecked = true
					disabledSelectedRadioButton.text = it.first
					disabledSelectedRadioButton.layoutParams = params
					radiobuttonEnabledContainerView.addView(radioButton)
					radiobuttonEnabledContainerView.addView(radioSelectedButton)
					radiobuttonDisabledContainerView.addView(disabledRadioButton)
					radiobuttonDisabledContainerView.addView(disabledSelectedRadioButton)
				}
			}

			if (radioButtonStylesList.isEmpty()) {
				radiobuttonContainerView.visibility = View.GONE
			} else {
				radiobuttonEnabledContainerView.removeAllViews()
				radiobuttonDisabledContainerView.removeAllViews()
				radioButtonStylesList.forEach {
					val radioButton = AppCompatRadioButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					radioButton.text = it.first
					radioButton.isClickable = true
					radioButton.layoutParams = params
					val disabledRadioButton = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledRadioButton.isEnabled = false
					disabledRadioButton.text = it.first
					disabledRadioButton.layoutParams = params

					val radioSelectedButton = AppCompatRadioButton(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					radioSelectedButton.text = it.first
					radioSelectedButton.isClickable = true
					radioSelectedButton.isChecked = true
					radioSelectedButton.layoutParams = params
					val disabledSelectedRadioButton = AppCompatCheckBox(ContextThemeWrapper(requireContext(), it.second), null, it.second)
					disabledSelectedRadioButton.isEnabled = false
					disabledSelectedRadioButton.isChecked = true
					disabledSelectedRadioButton.text = it.first
					disabledSelectedRadioButton.layoutParams = params
					radiobuttonEnabledContainerView.addView(radioButton)
					radiobuttonEnabledContainerView.addView(radioSelectedButton)
					radiobuttonDisabledContainerView.addView(disabledRadioButton)
					radiobuttonDisabledContainerView.addView(disabledSelectedRadioButton)
				}
			}
		}
	}

	private fun populateSpacings() {
		var spacingsList = listOf<Pair<String, Int>>()
		val dimens: Array<Field> = Class.forName(context?.packageName + ".R\$dimen").declaredFields
		for (dimen in dimens) {
			val dimenName: String = dimen.name
			val dimenId: Int = dimen.getInt(null)
			spacingsList = spacingsList.plus(Pair(dimenName, dimenId))
		}

		if (spacingsList.isEmpty()) {
			binding?.layoutContainerView?.visibility = View.GONE
		} else {
			binding?.layoutRecyclerView?.adapter = SpacingAdapter(spacingsList)
		}
	}

	private fun populateColors() {
		val colorList: HashMap<String, List<Pair<String, Int>>> = hashMapOf()
		val colors: Array<Field> = Class.forName(context?.packageName + ".R\$color").declaredFields
		for (color in colors) {
			val colorName: String = color.name
			if (!colorName.startsWith("guidestyle", true)) {
				val colorId: Int = color.getInt(null)
				var list = colorList[colorName.firstPart()]
				if (list != null) {
					list = list.plus(Pair(colorName, colorId))
					colorList[colorName.firstPart()] = list
				} else {
					list = listOf()
					list = list.plus(Pair(colorName, colorId))
					colorList[colorName.firstPart()] = list
				}
			}
		}

		if (colorList.isEmpty()) {
			binding?.colorContainerView?.visibility = View.GONE
		} else {
			binding?.colorRecyclerView?.adapter = ColorAdapter(colorList)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onDismiss(dialog: DialogInterface) {
		super.onDismiss(dialog)

		(activity as? ShakeSensorListener.StyleGuideHandler)?.registerListener()
	}
}