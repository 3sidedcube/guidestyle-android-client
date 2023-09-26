package com.cube.styleguide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cube.styleguide.databinding.ActivityGuidestyleBinding

class StyleGuideActivity : AppCompatActivity() {

	companion object {
		const val KEY_TEXT_STYLE_PREFIXES_LIST = "KEY_TEXT_STYLE_PREFIXES_LIST"

		/**
		 * @param textPrefixesList - it can be list of strings like: "Body", "Heading", "Caption", "Subtitle", "Bold", "Regular"
		 * the logic will be searching these represented prefixes in the styles.xml and classify them as textStyles
		 * */
		fun getIntent(context: Context, textPrefixesList: ArrayList<String>?) = Intent(context, StyleGuideActivity::class.java).apply {
			this.putStringArrayListExtra(KEY_TEXT_STYLE_PREFIXES_LIST, textPrefixesList)
		}
	}

	private lateinit var binding: ActivityGuidestyleBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGuidestyleBinding.inflate(layoutInflater)
		setContentView(binding.root)

		StyleGuideFragment.getInstance(
			textStylePrefixesList = intent.getStringArrayListExtra(KEY_TEXT_STYLE_PREFIXES_LIST)
		).show(supportFragmentManager, StyleGuideFragment::class.java.name)
	}
}