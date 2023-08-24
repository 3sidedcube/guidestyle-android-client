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
import androidx.core.view.isVisible
import com.cube.styleguide.adapter.ColorAdapter
import com.cube.styleguide.adapter.SpacingAdapter
import com.cube.styleguide.adapter.TextStylesAdapter
import com.cube.styleguide.databinding.FragmentStyleGuideBinding
import com.cube.styleguide.fragments.BottomSheetFragment
import com.cube.styleguide.utils.Extensions.firstPart
import com.cube.styleguide.utils.ShakeSensorListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.radiobutton.MaterialRadioButton
import java.lang.reflect.Field

open class StyleGuideFragment : BottomSheetFragment(R.layout.fragment_style_guide) {
    private var binding: FragmentStyleGuideBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStyleGuideBinding.inflate(inflater, container, false)
        binding?.closeButton?.setOnClickListener { dismiss() }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val packageName = getPackageName()
        if (packageName.isEmpty()) {
            binding?.apply {
                nestedScrollView.visibility = View.GONE
                errorMessage.text = getString(R.string.guidestyle_failed_to_load_the_package, context?.packageName!!)
                errorMessage.visibility = View.VISIBLE
            }
        } else {

            populateColors(packageName)

            populateSpacings(packageName)

            populateStyles(packageName)
        }
    }

    /**
     * Tries to recover the package name of the app.
     * In case the app has multiple flavours but they do not have different res(colors,dimens, style) it will fall back on the main flavour (aka production flavour)
     *
     * @return package name or empty string
     */
    private fun getPackageName(): String {
        val packageName = context?.packageName!!
        try {
            Class.forName("$packageName.R\$style").declaredFields
        } catch (cne: ClassNotFoundException) {
            return if (packageName.endsWith(".staging", true)) {
                packageName.replace(".staging", "")
            } else if (packageName.endsWith(".test", true)) {
                packageName.replace(".test", "")
            } else if (packageName.endsWith(".dev", true)) {
                packageName.replace(".dev", "")
            } else {
                ""
            }
        }
        return packageName
    }

    /**
     * Call this function to populate the style guide with your button styles, and to choose the component type.
     * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
     * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
     * @param usesMaterialComponents Identifies whether styles for this component should be applied to a materialComponent or an appCompatComponent.
     */
    fun populateButtonStyles(
        themes: Array<Field>,
        prefixesList: List<String> = listOf("Button", "button"),
        usesMaterialComponents: Boolean = false
    ) {
        val buttonStylesList = mutableListOf<Pair<String, Int>>()

        themes.forEach { theme ->
            if (prefixesList.any { theme.name.startsWith(it) }) {
                buttonStylesList.add(Pair(theme.name, theme.getInt(null)))
            }
        }

        addViewsToRelevantSection("Button", buttonStylesList, false)
    }

    /**
     * Call this function to populate the style guide with your text styles.
     * @param themes A list of styles these can be retrieved using Class.forName("$packageName.R\$style").declaredFields.
     * @param prefixesList A list of prefixes used in your styles, by default this is a list of commonly used prefixes.
     */
    fun populateTextStyles(
        themes: Array<Field>,
        prefixesList: List<String> = listOf("Body", "Heading", "Caption", "Subtitle", "Bold", "Regular")
    ) {
        val textStylesList = mutableListOf<Pair<String, Int>>()

        themes.forEach { theme ->
            if (prefixesList.any { theme.name.startsWith(it) }) {
                textStylesList.add(Pair(theme.name, theme.getInt(null)))
            }
        }

        addViewsToRelevantSection("Text", textStylesList, false)
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

        addViewsToRelevantSection("Checkbox", checkboxStylesList, false)
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

        addViewsToRelevantSection("RadioButton", radiobuttonStyleList, false)
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

        addViewsToRelevantSection("Switch", switchStyleList, false)
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
            "Button" -> {
                binding?.apply {
                    buttonContainerView.isVisible = true
                    buttonEnabledContainerView.removeAllViews()
                    buttonDisabledContainerView.removeAllViews()

                    stylesList.forEach {
                        val (button, disabledButton) = if (usesMaterialComponents) {
                            Pair(MaterialButton(ContextThemeWrapper(requireContext(), it.second), null, it.second), MaterialButton(ContextThemeWrapper(requireContext(), it.second), null, it.second))
                        } else {
                            Pair(AppCompatButton(ContextThemeWrapper(requireContext(), it.second), null, it.second), AppCompatButton(ContextThemeWrapper(requireContext(), it.second), null, it.second))
                        }

                        button.text = it.first
                        button.layoutParams = params
                        button.isClickable = true

                        disabledButton.text = it.first
                        disabledButton.isEnabled = false
                        disabledButton.layoutParams = params
                        buttonEnabledContainerView.addView(button)

                        buttonDisabledContainerView.addView(disabledButton)
                    }
                }
            }

            "Text" -> {
                binding?.apply {
                    textContainerView.isVisible = true
                    textRecyclerView.adapter = TextStylesAdapter(stylesList)
                }
            }

            "Checkbox" -> {
                binding?.apply {
                    checkboxContainer.isVisible = true
                    checkboxEnabledContainerView.removeAllViews()
                    checkboxDisabledContainerView.removeAllViews()

                    stylesList.forEach { style ->
                        val (checkboxChecked, checkboxNotChecked, checkboxCheckedDisabled, checkboxNotCheckedDisabled) = List(4) {
                            if (usesMaterialComponents) {
                                MaterialCheckBox(ContextThemeWrapper(requireContext(), style.second), null, style.second)
                            } else {
                                AppCompatCheckBox(ContextThemeWrapper(requireContext(), style.second), null, style.second)
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
                                MaterialRadioButton(ContextThemeWrapper(requireContext(), style.second), null, style.second)
                            } else {
                                AppCompatRadioButton(ContextThemeWrapper(requireContext(), style.second), null, style.second)
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
                                MaterialSwitch(ContextThemeWrapper(requireContext(), style.second), null, style.second)
                            } else {
                                SwitchCompat(ContextThemeWrapper(requireContext(), style.second), null, style.second)
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
        populateButtonStyles(themes)
        populateTextStyles(themes)
        populateCheckboxStyles(themes)
        populateRadioButtonStyles(themes)
        populateSwitchStyles(themes)
    }

    private fun populateSpacings(packageName: String) {
        var spacingsList = listOf<Pair<String, Int>>()
        val dimens: Array<Field> = Class.forName("$packageName.R\$dimen").declaredFields
        for (dimen in dimens) {
            val dimenName: String = dimen.name
            val dimenId: Int = dimen.getInt(null)
            spacingsList = spacingsList.plus(Pair(dimenName, dimenId))
        }
        binding?.apply {
            if (spacingsList.isEmpty()) {
                layoutContainerView.visibility = View.GONE
            } else {
                layoutContainerView.visibility = View.VISIBLE
                layoutRecyclerView.adapter = SpacingAdapter(spacingsList)
            }
        }
    }

    private fun populateColors(packageName: String) {
        val colorList: HashMap<String, List<Pair<String, Int>>> = hashMapOf()
        val colors: Array<Field> = Class.forName("$packageName.R\$color").declaredFields
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
        binding?.apply {
            if (colorList.isEmpty()) {
                colorContainerView.visibility = View.GONE
            } else {
                colorContainerView.visibility = View.VISIBLE
                binding?.colorRecyclerView?.adapter = ColorAdapter(colorList)
            }
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
