package com.kushal.ftpImageLoading.ui.browse

import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter

import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.TVApplication
import com.kushal.ftpImageLoading.presenter.browse.BrowseDataPresenter

import java.util.ArrayList
import java.util.HashMap

class BrowseFragment : BrowseSupportFragment() {

    private var mCategoryRowAdapter: ArrayObjectAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUIElements()
        prepareEntranceTransition()
        mCategoryRowAdapter = ArrayObjectAdapter(ListRowPresenter())

        setBrowseData()
    }

    private fun setUIElements() {
        title = getString(R.string.browse_title)
        headersState = BrowseSupportFragment.HEADERS_ENABLED
    }

    private fun setBrowseData() {
        val ftpDataMap = TVApplication.appInstance.ftpDirectoriesData
        if (ftpDataMap != null) {
            for (categoryName in ftpDataMap.keys) {
                val header = HeaderItem(categoryName)
                val arrayAdapter = ArrayObjectAdapter(BrowseDataPresenter())
                for (imageName in ftpDataMap[categoryName]!!) {
                    arrayAdapter.add("$categoryName/$imageName")
                }
                val row = ListRow(header, arrayAdapter)
                mCategoryRowAdapter!!.add(row)
            }
            adapter = mCategoryRowAdapter
        }
    }
}
