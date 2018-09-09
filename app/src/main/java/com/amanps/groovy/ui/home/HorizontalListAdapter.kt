package com.amanps.groovy.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recyclerview_horizontal_item.view.*

class HorizontalListAdapter(val context: Context, private val programs: List<Program>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_horizontal_item, parent, false))
    }

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageUrl = NetworkUtils.getPosterImageUrl(programs?.get(position)?.poster_path ?: "")
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_placeholder))
                .into(holder.itemView.program_image)
    }
}