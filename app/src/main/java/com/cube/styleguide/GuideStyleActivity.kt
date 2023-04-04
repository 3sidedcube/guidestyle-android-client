package com.cube.styleguide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cube.styleguide.databinding.ActivityGuidestyleBinding

class GuideStyleActivity : AppCompatActivity() {

	private lateinit var binding: ActivityGuidestyleBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGuidestyleBinding.inflate(layoutInflater)
		setContentView(binding.root)

		GuideStyleFragment().show(supportFragmentManager, GuideStyleFragment::class.java.name)
	}
}