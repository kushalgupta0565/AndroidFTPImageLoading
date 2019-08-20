package com.kushal.ftpImageLoading

import android.app.Application

import java.util.ArrayList
import java.util.HashMap

class TVApplication : Application() {
    var ftpDirectoriesData: HashMap<String, ArrayList<String>>? = null

    companion object {
        val appInstance = TVApplication()
    }
}
