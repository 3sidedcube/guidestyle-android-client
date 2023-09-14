package com.cube.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cube.styleguide.StyleGuideFragment

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val tag = StyleGuideFragment::class.java.name
		val existingFragment = supportFragmentManager.findFragmentByTag(tag) != null
		if (!existingFragment) StyleGuideFragment().show(supportFragmentManager, tag)
	}
}