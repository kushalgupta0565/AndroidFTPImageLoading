package com.kushal.ftpImageLoading.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.kushal.ftpImageLoading.ui.image.ImageActivity
import com.kushal.ftpImageLoading.ui.image.ImageFragment
import java.util.ArrayList

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val imageIntent = Intent(context, ImageActivity::class.java)
        imageIntent.putExtra(ImageFragment.IMAGE_LIST_DATA, intent?.getSerializableExtra(ImageFragment.IMAGE_LIST_DATA) as ArrayList<String>)
        imageIntent.putExtra(ImageFragment.IS_AUTOPLAY, intent.extras!!.getBoolean(ImageFragment.IS_AUTOPLAY))
        context?.startActivity(imageIntent)
    }
}