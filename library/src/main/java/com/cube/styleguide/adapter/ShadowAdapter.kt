package com.cube.styleguide.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cube.styleguide.adapter.viewholder.ShadowItemViewHolder
import com.cube.styleguide.databinding.ShadowItemViewBinding
import com.cube.styleguide.utils.Extensions.layoutInflater

class ShadowAdapter(private val shadows: List<Pair<String, Float>>) : RecyclerView.Adapter<ShadowItemViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ShadowItemViewHolder(ShadowItemViewBinding.inflate(parent.context.layoutInflater, parent, false))

	override fun getItemCount() = shadows.size

	override fun onBindViewHolder(holder: ShadowItemViewHolder, position: Int) {
		holder.populate(shadows[position])
	}
}