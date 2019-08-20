package com.kushal.ftpImageLoading.presenter.browse

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.glideFTP.FTPModel
import com.kushal.ftpImageLoading.utils.Constants

class BrowseDataPresenter : Presenter() {

    private var mSelectedBackgroundColor = -1
    private var mDefaultBackgroundColor = -1
    internal lateinit var context: Context
    internal lateinit var mDefaultCardImage: Drawable

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {

        context = parent.context

        mDefaultBackgroundColor = ContextCompat.getColor(parent.context, R.color.default_background)
        mSelectedBackgroundColor = ContextCompat.getColor(parent.context, R.color.selected_background)
        mDefaultCardImage = parent.resources.getDrawable(R.drawable.movie, null)

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }

        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        updateCardBackgroundColor(cardView, false)
        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val imageData = item as String

        val cardView = viewHolder.view as ImageCardView
        val res = cardView.resources
        val width = res.getDimensionPixelSize(R.dimen.card_width)
        val height = res.getDimensionPixelSize(R.dimen.card_height)
        cardView.setMainImageDimensions(width, height)

        val complete_image_path = "ftp://" + Constants.NETWORK_HOSTNAME + ":" + Constants.PORT_NO + Constants.NETWORK_FOLDER_NAME + imageData
        val imageFile = Constants.NETWORK_FOLDER_NAME + imageData

        val ftpModel = FTPModel(imageFile)

        Glide.with(context).load(ftpModel)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(cardView.mainImageView)

    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        val cardView = viewHolder.view as ImageCardView

        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) mSelectedBackgroundColor else mDefaultBackgroundColor
        view.setBackgroundColor(color)
        view.findViewById<View>(R.id.info_field).setBackgroundColor(color)
    }
}
