package com.amanps.groovy.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import kotlinx.android.synthetic.main.recyclerview_horizontal_item.view.*

class HorizontalListAdapter(private val programs: List<Program>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_horizontal_item, parent, false))
    }

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.textview_program_title.text = programs?.get(position)?.title ?:
                programs?.get(position)?.name ?: "empty"
    }
}