package com.amanps.groovy.ui.detail

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
import kotlinx.android.synthetic.main.item_image_text_card.view.*

class RecommendationsRecyclerAdapter(val context: Context,
                                     val programsList: List<Program>,
                                     val programClickListener: ((program: Program) -> Unit))
    : RecyclerView.Adapter<RecommendationsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_text_card, parent, false))
    }

    override fun getItemCount(): Int {
        return programsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = NetworkUtils.getPosterImageUrl(
                programsList[position].poster_path ?: ""
        )
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_placeholder))
                .into(holder.itemView.imageview)

        holder.itemView.textview_name.text = programsList[position].title ?: programsList[position].name

        holder.itemView.setOnClickListener { programClickListener.invoke(programsList[position]) }
    }
}