package com.amanps.groovy.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.util.VIEW_TYPE_HORIZONTAL_LIST
import kotlinx.android.synthetic.main.recyclerview_horizontal.view.*

class HomeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Ordered map of HomePageViewTypes to list of programs.
     */
    var sections = listOf<HomeListSectionModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val recycledViewPool = RecyclerView.RecycledViewPool()

    class HorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HORIZONTAL_LIST -> {
                val horizontalView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_horizontal, parent, false)
                HorizontalViewHolder(horizontalView)
            }
            else -> {
                throw IllegalArgumentException("viewType in HomeAdapter#onCreateViewHolder is faulty.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_HORIZONTAL_LIST -> {
                holder.itemView.textview_section_name.text =
                        context.getString(sections[position].sectionNameResId)
                holder.itemView.recyclerview_horizontal.apply {
                    adapter = HorizontalRecyclerAdapter(context, sections[position])
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setRecycledViewPool(this@HomeAdapter.recycledViewPool)
                }
            }
            else -> { throw IllegalArgumentException("viewType in HomeAdapter#onBindViewHolder is faulty.") }
        }
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun getItemViewType(position: Int): Int {
        return sections[position].type
    }
}