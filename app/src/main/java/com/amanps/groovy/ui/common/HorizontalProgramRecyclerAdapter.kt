package com.amanps.groovy.ui.common

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

class HorizontalProgramRecyclerAdapter(val context: Context,
                                       val programs: List<Program>,
                                       val programClickListener: ((program: Program) -> Unit),
                                       val shouldDisplayTitle: Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_text_card, parent, false))
    }

    override fun getItemCount() = programs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageUrl = NetworkUtils.getPosterImageUrl(
                programs[position].poster_path ?: ""
        )
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_placeholder))
                .into(holder.itemView.imageview)

        holder.itemView.setOnClickListener {
            programClickListener.invoke(programs[position])
        }

        holder.itemView.textview_name.apply {
            visibility = if (shouldDisplayTitle) View.VISIBLE else View.GONE
            text = programs[position].title ?: programs[position].name
        }
    }
}