package com.amanps.groovy.ui.home

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_banner_image.view.*

class BannerPagerAdapter(val context: Context,
                         private val bannerPrograms: List<Program>) : PagerAdapter() {

    override fun isViewFromObject(view: View, any: Any) = view == (any as FrameLayout)

    override fun getCount() = bannerPrograms.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_banner_image, container, false)

        Glide.with(context)
                .load(NetworkUtils.getPosterImageUrl(bannerPrograms[position].backdrop_path ?: ""))
                .apply(RequestOptions()
                        .placeholder(R.drawable.image_placeholder)
                        .centerCrop())
                .into(view.imageview_banner)

        view.textview_program_title.text = bannerPrograms[position].title ?: bannerPrograms[position].name

        (container as ViewPager).addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}