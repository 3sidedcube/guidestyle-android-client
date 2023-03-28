package com.cube.styleguide.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.cube.styleguide.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetFragment(@LayoutRes contentLayoutId: Int) : BottomSheetDialogFragment(contentLayoutId) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val bottomSheet = view.parent as View
		val behavior = BottomSheetBehavior.from(bottomSheet)
		behavior.skipCollapsed = true
		behavior.isHideable = false
		behavior.isDraggable = false
		behavior.state = BottomSheetBehavior.STATE_EXPANDED
		bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
		bottomSheet.setBackgroundColor(Color.TRANSPARENT)
	}

	override fun getTheme() = R.style.BottomSheetDialogTheme
}