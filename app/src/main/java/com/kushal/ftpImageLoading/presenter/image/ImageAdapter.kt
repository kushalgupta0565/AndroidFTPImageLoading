package com.kushal.ftpImageLoading.presenter.image

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushal.ftpImageLoading.glideFTP.FTPModel
import com.kushal.ftpImageLoading.utils.Constants

import java.util.ArrayList

class ImageAdapter(internal var dataList: ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        val imageFile = Constants.NETWORK_FOLDER_NAME + dataList[position]

        val ftpModel = FTPModel(imageFile)

        Glide.with(container.context).load(ftpModel)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(imageView)

        container.addView(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}
