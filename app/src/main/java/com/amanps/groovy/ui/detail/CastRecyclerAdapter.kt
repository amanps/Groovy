package com.amanps.groovy.ui.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.data.model.CastModel
import com.amanps.groovy.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image_text_card.view.*

class CastRecyclerAdapter(val context: Context,
                          val castList: List<CastModel>,
                          val castClickListener: ((cast: CastModel) -> Unit))
    : RecyclerView.Adapter<CastRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_text_card, parent, false))
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = NetworkUtils.getPosterImageUrl(
                castList[position].profile_path ?: ""
        )
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_placeholder))
                .into(holder.itemView.imageview)

        holder.itemView.textview_name.text = castList[position].name

        holder.itemView.setOnClickListener { castClickListener.invoke(castList[position]) }
    }
}