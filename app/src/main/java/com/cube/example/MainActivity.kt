package com.cube.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cube.styleguide.StyleGuideFragment

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		StyleGuideFragment().show(supportFragmentManager, StyleGuideFragment::class.java.name)
	}
}