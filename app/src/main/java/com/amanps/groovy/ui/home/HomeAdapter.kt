package com.amanps.groovy.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.common.HorizontalProgramRecyclerAdapter
import com.amanps.groovy.util.VIEW_TYPE_BANNER_VIEW_PAGER
import com.amanps.groovy.util.VIEW_TYPE_HORIZONTAL_LIST
import kotlinx.android.synthetic.main.recyclerview_horizontal_sectioned.view.*
import kotlinx.android.synthetic.main.viewpager_banner.view.*

class HomeAdapter(val context: Context,
                  val programClickListener: ((program: Program) -> Unit)) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Ordered map of HomePageViewTypes to list of programs.
     */
    var sections = mutableListOf<HomeListSectionModel>()

    private val recycledViewPool = RecyclerView.RecycledViewPool()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HORIZONTAL_LIST -> {
                val horizontalView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_horizontal_sectioned, parent, false)
                ViewHolder(horizontalView)
            }
            VIEW_TYPE_BANNER_VIEW_PAGER -> {
                val viewPager = LayoutInflater.from(parent.context)
                        .inflate(R.layout.viewpager_banner, parent, false)
                ViewHolder(viewPager)
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
                    adapter = HorizontalProgramRecyclerAdapter(context, sections[position].programs,
                            this@HomeAdapter.programClickListener, false)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setRecycledViewPool(this@HomeAdapter.recycledViewPool)
                }
            }
            VIEW_TYPE_BANNER_VIEW_PAGER -> {
                holder.itemView.viewpager_banner.adapter = BannerPagerAdapter(context, sections[position].programs)
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