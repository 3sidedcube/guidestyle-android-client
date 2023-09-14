package com.cube.styleguide.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cube.styleguide.adapter.viewholder.BaseSpacingItemViewHolder

abstract class BaseSpacingAdapter<VH : BaseSpacingItemViewHolder<VB>, VB : ViewBinding>(private val dimens: List<Pair<String, Int>>) : RecyclerView.Adapter<VH>() {
	abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
	override fun getItemCount() = dimens.size
	override fun onBindViewHolder(holder: VH, position: Int) = holder.populate(dimens[position])
}