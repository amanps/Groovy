package com.amanps.groovy.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.util.HomePageViewTypes
import kotlinx.android.synthetic.main.recyclerview_horizontal.view.*

class HomeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Ordered map of HomePageViewTypes to list of programs.
     */
    var sectionedDataMap = linkedMapOf<Int, List<Program>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HorizontalViewHolder(context: Context, view: View, viewType: Int,
                               sectionedDataMap: LinkedHashMap<Int, List<Program>>) : RecyclerView.ViewHolder(view) {
        init {
            view.recyclerview_horizontal.apply {
                adapter = HorizontalListAdapter(context, sectionedDataMap[viewType])
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            in HomePresenter.homePageHorizontalSectionTypes -> {
                val horizontalView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_horizontal, parent, false)
                HorizontalViewHolder(context, horizontalView, viewType, sectionedDataMap)
            }
            else -> {
                throw IllegalArgumentException("viewType in HomeAdapter#onCreateViewHolder is faulty.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            in HomePresenter.homePageHorizontalSectionTypes -> {
                holder.itemView.textview_section_name.text =
                        context.getString(HomePageViewTypes.getSectionNameResourceId(getItemViewType(position)))
            }
            else -> { throw IllegalArgumentException("viewType in HomeAdapter#onBindViewHolder is faulty.") }
        }
    }

    override fun getItemCount(): Int {
        return sectionedDataMap.size
    }

    override fun getItemViewType(position: Int): Int {
        return sectionedDataMap.keys.elementAt(position)
    }
}