package com.cube.styleguide

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.cube.styleguide.StyleGuideActivity.Companion.KEY_TEXT_STYLE_PREFIXES_LIST
import com.cube.styleguide.adapter.ColorAdapter
import com.cube.styleguide.adapter.HorizontalSpacingAdapter
import com.cube.styleguide.adapter.RadiusAdapter
import com.cube.styleguide.adapter.ShadowAdapter
import com.cube.styleguide.adapter.SpacingAdapter
import com.cube.styleguide.adapter.TextStylesAdapter
import com.cube.styleguide.databinding.FragmentStyleGuideBinding
import com.cube.styleguide.databinding.ItemButtonContainerBinding
import com.cube.styleguide.fragments.BottomSheetFragment
import com.cube.styleguide.stylehandlers.ButtonsHandler
import com.cube.styleguide.stylehandlers.ColorsHandler
import com.cube.styleguide.stylehandlers.HorizontalSpacingHandler
import com.cube.styleguide.stylehandlers.RadiusHandler
import com.cube.styleguide.stylehandlers.ScrollEndHandler
import com.cube.styleguide.stylehandlers.ShadowsHandler
import com.cube.styleguide.stylehandlers.SpacingHandler
import com.cube.styleguide.stylehandlers.TextStylesHandler
import com.cube.styleguide.utils.Extensions.getPackageNameFlavorAdapted
import com.cube.styleguide.utils.ShakeSensorListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.radiobutton.MaterialRadioButton
import java.lang.reflect.Field

open class StyleGuideFragment : BottomSheetFragment(R.layout.fragment_style_guide) {
	private var binding: FragmentStyleGuideBinding? = null

	companion object {
		fun getInstance(textStylePrefixesList: List<String>?) = StyleGuideFragment().also {
			textStylePrefixesList?.let { prefixesList ->
				it.arguments = bundleOf(KEY_TEXT_STYLE_PREFIXES_LIST to prefixesList)
			}
		}
	}

	private val getTextStylePrefixesList get() = arguments?.getStringArrayList(KEY_TEXT_STYLE_PREFIXES_LIST)

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentStyleGuideBinding.inflate(inflater, container, false)
		binding?.closeButton?.setOnClickListener { dismiss() }
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val packageName = requireContext().getPackageNameFlavorAdapted()
		if (packageName.isEmpty()) {
			binding?.apply {
				nestedScrollView.visibility = View.GONE
				errorMessage.text = getString(R.string.guidestyle_failed_to_load_the_package, context?.packageName!!)
				errorMessage.visibility = View.VISIBLE
			}
		} else {

			populateColorsAndShadow()

			populateSpacings()

			populateRadius()

			populateScrollEnd()

			populateStyles(packageName)
		}
	}

	/**
	 * Call this function to populate the style guide with your button styles, and to choose the component type.
	 * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
	 * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
	 * @param usesMaterialComponents Identifies whether styles for this component should be applied to a materialComponent or an appCompatComponent.
	 */
	private fun populateButtonStyles() {
		val stylesList = ButtonsHandler.getButtonsStyles(requireContext())
		binding?.buttonContainerView?.isVisible = stylesList != null
		stylesList ?: return

		val usesMaterialComponents = false

		val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
		val bottom = resources.getDimensionPixelSize(R.dimen.guidestyle_largest_spacing)
		val vertical = resources.getDimensionPixelSize(R.dimen.guidestyle_small_spacing)
		params.setMargins(vertical, 0, vertical, bottom)

		stylesList.forEach {
			val container = ItemButtonContainerBinding.inflate(layoutInflater)
			val (button, disabledButton) = if (usesMaterialComponents) Pair(getMaterialButton(it), getMaterialButton(it))
			else Pair(getAppCompatButton(it), getAppCompatButton(it))

			button.text = "Active"
			button.layoutParams = params
			button.isClickable = true

			disabledButton.text = "Inactive"
			disabledButton.isEnabled = false
			disabledButton.layoutParams = params
			container.buttonContainer.addView(button)

			container.sectionLabel.text = it.first
			container.buttonContainer.addView(disabledButton)

			binding?.buttonsContainer?.addView(container.root)
		}
	}

	private fun getMaterialButton(style: Pair<String, Int>) = MaterialButton(ContextThemeWrapper(requireContext(), style.second), null, style.second)
	private fun getAppCompatButton(style: Pair<String, Int>) = AppCompatButton(ContextThemeWrapper(requireContext(), style.second), null, style.second)

	/**
	 * Call this function to populate the style guide with your text styles.
	 * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
	 * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
	 */
	private fun populateTextStyles() {
		binding?.apply {
			textContainerView.isVisible = true
			val textStyles = getTextStylePrefixesList ?: listOf("Body", "Heading", "Caption", "Subtitle", "Bold", "Regular")
			textRecyclerView.adapter = TextStylesAdapter(TextStylesHandler.getTextStyles(requireContext(), textStyles) ?: return)
		}
	}

	/**
	 * Call this function to populate the style guide with your checkbox styles, and to choose the component type.
	 * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
	 * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
	 * @param usesMaterialComponents Identifies whether styles for this component should be applied to a materialComponent or an appCompatComponent.
	 */
	fun populateCheckboxStyles(
		themes: Array<Field>,
		prefixesList: List<String> = listOf("Checkbox", "checkbox", "CheckBox"),
		usesMaterialComponents: Boolean = false
	) {
		val checkboxStylesList = mutableListOf<Pair<String, Int>>()

		themes.forEach { theme ->
			if (prefixesList.any { theme.name.startsWith(it) }) {
				checkboxStylesList.add(Pair(theme.name, theme.getInt(null)))
			}
		}

		addViewsToRelevantSection("Checkbox", checkboxStylesList, usesMaterialComponents)
	}

	/**
	 * Call this function to populate the style guide with your radio button styles, and to choose the component type.
	 * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
	 * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
	 * @param usesMaterialComponents Identifies whether styles for this component should be applied to a materialComponent or an appCompatComponent.
	 */
	fun populateRadioButtonStyles(
		themes: Array<Field>,
		prefixesList: List<String> = listOf("RadioButton", "Radiobutton", "radiobutton"),
		usesMaterialComponents: Boolean = false
	) {
		val radiobuttonStyleList = mutableListOf<Pair<String, Int>>()

		themes.forEach { theme ->
			if (prefixesList.any { theme.name.startsWith(it) }) {
				radiobuttonStyleList.add(Pair(theme.name, theme.getInt(null)))
			}
		}

		addViewsToRelevantSection("RadioButton", radiobuttonStyleList, usesMaterialComponents)
	}

	/**
	 * Call this function to populate the style guide with your switch styles, and to choose the component type.
	 * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
	 * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
	 * @param usesMaterialComponents Identifies whether styles for this component should be applied to a materialComponent or an appCompatComponent.
	 */
	fun populateSwitchStyles(
		themes: Array<Field>,
		prefixesList: List<String> = listOf("Switch", "switch"),
		usesMaterialComponents: Boolean = false
	) {
		val switchStyleList = mutableListOf<Pair<String, Int>>()

		themes.forEach { theme ->
			if (prefixesList.any { theme.name.startsWith(it) }) {
				switchStyleList.add(Pair(theme.name, theme.getInt(null)))
			}
		}

		addViewsToRelevantSection("Switch", switchStyleList, usesMaterialComponents)
	}

	/**
	 * @param componentName A string name for the view component.
	 * @param stylesList A list of styles for the component.
	 * @param usesMaterialComponents Tells the function whether to create the views using material components or app compat components.
	 */
	private fun addViewsToRelevantSection(componentName: String, stylesList: List<Pair<String, Int>>, usesMaterialComponents: Boolean = false) {
		if (stylesList.isEmpty()) return

		val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
		val bottom = resources.getDimensionPixelSize(R.dimen.guidestyle_largest_spacing)
		val vertical = resources.getDimensionPixelSize(R.dimen.guidestyle_small_spacing)
		params.setMargins(vertical, 0, vertical, bottom)


		when (componentName) {
			"Checkbox" -> {
				binding?.apply {
					checkboxContainerView.isVisible = true
					checkboxEnabledContainerView.removeAllViews()
					checkboxDisabledContainerView.removeAllViews()

					stylesList.forEach { style ->
						val (checkboxChecked, checkboxNotChecked, checkboxCheckedDisabled, checkboxNotCheckedDisabled) = List(4) {
							if (usesMaterialComponents) {
								MaterialCheckBox(ContextThemeWrapper(requireContext(), style.second))
							} else {
								AppCompatCheckBox(ContextThemeWrapper(requireContext(), style.second))
							}
						}.onEach {
							it.text = style.first
							it.layoutParams = params
						}

						checkboxChecked.isChecked = true
						checkboxCheckedDisabled.isChecked = true
						checkboxCheckedDisabled.isEnabled = false
						checkboxNotCheckedDisabled.isEnabled = false

						checkboxEnabledContainerView.addView(checkboxChecked)
						checkboxEnabledContainerView.addView(checkboxNotChecked)
						checkboxDisabledContainerView.addView(checkboxCheckedDisabled)
						checkboxDisabledContainerView.addView(checkboxNotCheckedDisabled)
					}
				}
			}

			"RadioButton" -> {
				binding?.apply {
					radiobuttonContainerView.isVisible = true
					radiobuttonEnabledContainerView.removeAllViews()
					radiobuttonDisabledContainerView.removeAllViews()

					stylesList.forEach { style ->
						val (radioButtonChecked, radioButtonNotChecked, radioButtonCheckedDisabled, radioButtonNotCheckedDisabled) = List(4) {
							if (usesMaterialComponents) {
								MaterialRadioButton(ContextThemeWrapper(requireContext(), style.second))
							} else {
								AppCompatRadioButton(ContextThemeWrapper(requireContext(), style.second))
							}
						}.onEach {
							it.layoutParams = params
							it.text = style.first
						}

						radioButtonChecked.isChecked = true
						radioButtonCheckedDisabled.isChecked = true
						radioButtonCheckedDisabled.isEnabled = false
						radioButtonNotCheckedDisabled.isEnabled = false

						radiobuttonEnabledContainerView.addView(radioButtonChecked)
						radiobuttonEnabledContainerView.addView(radioButtonNotChecked)
						radiobuttonDisabledContainerView.addView(radioButtonCheckedDisabled)
						radiobuttonDisabledContainerView.addView(radioButtonNotCheckedDisabled)
					}
				}
			}

			"Switch" -> {
				binding?.apply {
					switchContainerView.visibility = View.VISIBLE
					switchEnabledContainerView.removeAllViews()
					switchDisabledContainerView.removeAllViews()

					stylesList.forEach { style ->
						val (switchChecked, switchNotChecked, switchCheckedDisabled, switchNotCheckedDisabled) = List(4) {
							if (usesMaterialComponents) {
								MaterialSwitch(ContextThemeWrapper(requireContext(), style.second))
							} else {
								SwitchCompat(ContextThemeWrapper(requireContext(), style.second))
							}
						}.onEach {
							it.layoutParams = params
							it.text = style.first
						}

						switchChecked.isChecked = true
						switchCheckedDisabled.isChecked = true
						switchCheckedDisabled.isEnabled = false
						switchNotCheckedDisabled.isEnabled = false

						switchEnabledContainerView.addView(switchChecked)
						switchEnabledContainerView.addView(switchNotChecked)
						switchDisabledContainerView.addView(switchCheckedDisabled)
						switchDisabledContainerView.addView(switchNotCheckedDisabled)
					}
				}
			}
		}
	}

	/**
	 * Function to be called when the fragment loads. It will call the various populate functions for the style guide. These functions can then be called again if you need to customise the content.
	 */
	private fun populateStyles(packageName: String) {
		val themes: Array<Field> = Class.forName("$packageName.R\$style").declaredFields
		populateButtonStyles()
		populateTextStyles()
		populateCheckboxStyles(themes)
		populateRadioButtonStyles(themes)
		populateSwitchStyles(themes)
	}

	private fun populateSpacings() {
		binding?.apply {
			val spacingsList = SpacingHandler.getSpacings(requireContext())
			spacingsList?.let {
				layoutRecyclerView.adapter = SpacingAdapter(spacingsList)
			}

			val horizontalSpacingsList = HorizontalSpacingHandler.getHorizontalSpacings(requireContext())
			horizontalSpacingsList?.let {
				layoutRecyclerViewHorizontalSpacing.adapter = HorizontalSpacingAdapter(horizontalSpacingsList)
			}

			layoutContainerView.isVisible = !spacingsList.isNullOrEmpty() || !horizontalSpacingsList.isNullOrEmpty()
		}
	}

	private fun populateRadius() {
		binding?.apply {
			val radiusList = RadiusHandler.getRadius(requireContext())
			radiusContainerView.isVisible = !radiusList.isNullOrEmpty()
			radiusRecyclerView.adapter = RadiusAdapter(radiusList ?: return)
		}
	}

	private fun populateScrollEnd() {
		binding?.apply {
			val scrollendsList = ScrollEndHandler.getScrollEnd(requireContext())
			scrollEndView.isVisible = !scrollendsList.isNullOrEmpty()
			scrollendsList ?: return
			viewScrollend.headerTitle.text = getString(R.string.guidestyle_padding)
			val size = scrollendsList[0].second
			viewScrollend.spacingInDp.text = getString(R.string.guidestyle_dp_text, size)
			viewScrollend.view.layoutParams.height = size
		}
	}

	private fun populateColorsAndShadow() {
		binding?.apply {
			val colorList = ColorsHandler.getColors(requireContext())
			colorList?.let {
				colorRecyclerView.adapter = ColorAdapter(it)
			}

			val shadowList = ShadowsHandler.getShadows(requireContext())
			shadowList?.let {
				shadowRecyclerView.adapter = ShadowAdapter(it)
			}

			colorContainerView.isVisible = !colorList.isNullOrEmpty() || !shadowList.isNullOrEmpty()
		}
	}

	/**
	 * Override the onViewCreated and call this function in order to add you custom views
	 */
	fun addCustomView(view: View) {
		binding?.customViews?.addView(view)
		binding?.customViewContainer?.visibility = View.VISIBLE
	}

	override fun onDestroyView() {
		super.onDestroyView()
		/**
		 * Move sure to remove the custom views, cos otherwise you need to create new views everytime due to the parent of the view being the previous instance of the Fragment
		 * This is not an issue for the rest of the views cos they get recreated everytime. While custom views remain in the [GuideStyleManager]
		 */
		binding?.customViews?.removeAllViews()
		binding = null
	}

	override fun onDismiss(dialog: DialogInterface) {
		super.onDismiss(dialog)

		(activity as? ShakeSensorListener.StyleGuideHandler)?.registerListener()
	}
}
