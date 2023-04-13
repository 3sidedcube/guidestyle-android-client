package com.cube.styleguide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cube.styleguide.databinding.ActivityGuidestyleBinding

class StyleGuideActivity : AppCompatActivity() {

	private lateinit var binding: ActivityGuidestyleBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGuidestyleBinding.inflate(layoutInflater)
		setContentView(binding.root)

		StyleGuideFragment().show(supportFragmentManager, StyleGuideFragment::class.java.name)
	}
}