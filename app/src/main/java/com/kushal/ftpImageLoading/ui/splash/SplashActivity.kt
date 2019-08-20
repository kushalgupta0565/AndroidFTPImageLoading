package com.kushal.ftpImageLoading.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity

import com.kushal.ftpImageLoading.utils.AppUtil
import com.kushal.ftpImageLoading.R
import com.kushal.ftpImageLoading.TVApplication
import com.kushal.ftpImageLoading.ui.browse.BrowseActivity

import java.util.ArrayList
import java.util.HashMap

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SplashActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        connectToFTP()
    }

    private fun connectToFTP() {
        AppUtil.getFTPObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    if (result != null) {
                        if (result is HashMap<*, *>) {
                            TVApplication.appInstance.ftpDirectoriesData = result as HashMap<String, ArrayList<String>>?
                            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
                            startSplashHandler()
                        } else
                            showErrorDialog(result as String)
                    } else {
                        showErrorDialog(result as String)
                    }
                }
    }

    private fun startSplashHandler() {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, BrowseActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }

    private fun showErrorDialog(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this, R.style.Theme_AppCompat)
                .setTitle("Error")
                .setMessage(msg)
                .setCancelable(true)
                .show()
    }

    companion object {
        val SPLASH_TIME: Long = 2000
    }
}