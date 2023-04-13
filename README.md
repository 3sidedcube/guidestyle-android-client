# Style Guide library

## What is the Style Guide lib? 
The StyleGuide lib is a designer tool that 3SidedCube has created in order to make sure that all the UI components match the designs before we implement all the screens. Designers and developers can verify that by reviewing all the colors, dimens, styles and the basic views (Buttons, RadioButtons, Switches, CheckBoxes) 

## Rules
In order the StyleGuide to pick up the Heading, Body, Subtitle, Captions styles, they should be written in the following format: 

```
	<style name="Heading.[the name you prefer etc Heading.One, Heading.Main]">
		<item name="android:textSize">24sp</item>
		<item name="android:lineHeight">32dp</item>
	</style>
	<style name="Body.[the name you prefer etc Body.One, Body.Description]">
		<item name="android:textSize">14sp</item>
		<item name="android:lineHeight">32dp</item>
	</style>
```

For better grouping of the color we recommend using the following way(etc namecolor_variation):
```
	<color name="green_500">#2A533E</color>
	<color name="green_400">#4F9C75</color>
	<color name="green_300">#69CD9A</color>
	<color name="green_200">#ADE4C8</color>
	<color name="green_100">#E2FAEE</color>
```

## Usage
You can just call the StyleGuide from your activity either as an Activity or as BottomSheetFragment:

```
startActivity(Intent(this, StyleGuideActivity::class.java))
```

or 
```
 StyleGuideFragment().show(supportFragmentManager, StyleGuideFragment::class.java.name)
```

# Hacks
Many times we will implement custom views in our projects. In order to show this custom views, we can extend the StyleGuideFragment and add the views that we want:
```
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
```

## Installation
To use in a project add the following to the repositories list in your root gradle file:
```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
  ```
Add the following dependency to the app gradle file:
  ```
  implementation 'com.github.3sidedcube:guidestyle-android-client:0.1.0'
  ```

## License

See LICENSE.md