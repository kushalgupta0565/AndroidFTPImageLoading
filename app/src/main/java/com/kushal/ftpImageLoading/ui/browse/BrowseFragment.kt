package com.kushal.ftpImageLoading.ui.browse

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.leanback.widget.picker.TimePicker

import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.TVApplication
import com.kushal.ftpImageLoading.presenter.browse.BrowseDataPresenter
import com.kushal.ftpImageLoading.ui.image.ImageActivity
import com.kushal.ftpImageLoading.ui.image.ImageFragment
import java.util.*

class BrowseFragment : BrowseSupportFragment() {

    private var mCategoryRowAdapter: ArrayObjectAdapter? = null
    companion object {
        val ALARM_REQUEST_ID = 78542
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUIElements()
        prepareEntranceTransition()
        mCategoryRowAdapter = ArrayObjectAdapter(ListRowPresenter())

        setBrowseData()
        onItemViewClickedListener = ItemClickListener()
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

    private inner class ItemClickListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any, rowViewHolder: RowPresenter.ViewHolder, row: Row)
        {
            val folderName = row.headerItem.name
            val folderData = getRowData(row)

            startIntent(folderData)

        }
    }

    private fun getRowData(row: Row): ArrayList<String> {
        val adapter = (row as ListRow).adapter as ArrayObjectAdapter
        val dataList = ArrayList<String>()
        for (i in 0 until adapter.size()) {
            dataList.add(adapter.get(i) as String)
        }
        return dataList
    }


    private fun startIntent(folderData: ArrayList<String>) {
        val intent = Intent(activity, ImageActivity::class.java)
        intent.putExtra(ImageFragment.IMAGE_LIST_DATA, folderData)
        intent.putExtra(ImageFragment.IS_AUTOPLAY, true)
        startActivity(intent)
    }

}
