package com.kushal.ftpImageLoading.ui.browse

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.TVApplication
import com.kushal.ftpImageLoading.presenter.browse.BrowseDataPresenter
import com.kushal.ftpImageLoading.receiver.AlarmReceiver
import com.kushal.ftpImageLoading.ui.image.ImageActivity
import com.kushal.ftpImageLoading.ui.image.ImageFragment
import java.util.*

class BrowseFragment : BrowseSupportFragment() {

    private var mCategoryRowAdapter: ArrayObjectAdapter? = null
    private val alarmReceiver = AlarmReceiver()

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
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any, rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            val folderName = row.headerItem.name
            val folderData = getRowData(row)

            timePickerDialog("$folderName selected with ${folderData.size} images", folderData)

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

    private fun timePickerDialog(title: String, folderData: ArrayList<String>) {
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, R.style.TimePickerDialogTheme,
                { view, hourOfDay, minute ->

                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)
                    c.set(Calendar.SECOND, 0)

                    startIntent(folderData, c.timeInMillis)
                }, mHour, mMinute, false)
        timePickerDialog.setTitle(title)
        timePickerDialog.show()
    }

    private fun startIntent(folderData: ArrayList<String>, timeMills: Long) {
        // Generate Intent
        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.putExtra(ImageFragment.IMAGE_LIST_DATA, folderData)
        intent.putExtra(ImageFragment.IS_AUTOPLAY, true)
        // Register intent with AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
                context, ALARM_REQUEST_ID, intent, 0)
        val alarmManager = context!!.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, timeMills, pendingIntent)
        registerReceiver()
    }

    private fun registerReceiver() {
        activity!!.registerReceiver(alarmReceiver, IntentFilter())
    }
}
