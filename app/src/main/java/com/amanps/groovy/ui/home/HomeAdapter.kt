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

    inner class HorizontalViewHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {
        init {
            view.recyclerview_horizontal.apply {
                adapter = HorizontalListAdapter(sectionedDataMap[viewType])
                layoutManager = LinearLayoutManager(this@HomeAdapter.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            in HomePresenter.homePageHorizontalSectionTypes -> {
                val horizontalView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_horizontal, parent, false)
                HorizontalViewHolder(horizontalView, viewType)
            }
            else -> {
                throw IllegalArgumentException("viewType in HomeAdapter is faulty.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // nothing for this adapter. Binding is handled by the individual HorizontalListAdapters.
    }

    override fun getItemCount(): Int {
        return sectionedDataMap.size
    }

    override fun getItemViewType(position: Int): Int {
        return sectionedDataMap.keys.elementAt(position)
    }
}