package com.kushal.ftpImageLoading.ui.image

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.presenter.image.ImageAdapter

import java.util.ArrayList

class ImageFragment : Fragment() {

    private var multi_image_pager: ViewPager? = null
    private var dataList: ArrayList<String>? = null
    private var isAutoplay = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_multi_image, container, false)
        multi_image_pager = rootView.findViewById(R.id.multi_image_pager)

        dataList = activity!!.intent.getSerializableExtra(IMAGE_LIST_DATA) as ArrayList<String>
        isAutoplay = activity!!.intent.extras!!.getBoolean(IS_AUTOPLAY)

        return multi_image_pager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multi_image_pager!!.adapter = ImageAdapter(dataList!!)
        if (isAutoplay)
            startAutoCycle()
    }

    fun startAutoCycle() {

        val mHandler = Handler()
        val mSliderRunnable = object : Runnable {
            override fun run() {

                try {
                    var currentPosition = multi_image_pager!!.currentItem
                    if (++currentPosition == dataList!!.size) {
                        currentPosition = 0
                    }
                    multi_image_pager!!.setCurrentItem(currentPosition, false)
                } finally {
                    mHandler.postDelayed(this, AUTO_PLAY_TIME.toLong())
                }

            }
        }
        mHandler.postDelayed(mSliderRunnable, AUTO_PLAY_TIME.toLong())
    }

    companion object {
        val TAG = ImageFragment::class.java.simpleName
        val IMAGE_LIST_DATA = "image_list_data"
        val IS_AUTOPLAY = "is_autoplay"
        private val AUTO_PLAY_TIME = 5 * 1000
    }
}
