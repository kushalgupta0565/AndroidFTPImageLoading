package com.kushal.ftpImageLoading.glideFTP

import android.util.Log

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.kushal.ftpImageLoading.utils.Constants

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

import java.io.IOException
import java.io.InputStream

class FTPDataFetcher internal constructor(private val model: FTPModel) : DataFetcher<InputStream> {
    private var stream: InputStream? = null
    internal var ftpClient: FTPClient? = null


    init {
        this.ftpClient = getFtpClient()
    }

    private fun getFtpClient(): FTPClient {
        val ftpClient = FTPClient()
        ftpClient.connectTimeout = 15 * 1000
        try {
            ftpClient.connect(Constants.NETWORK_HOSTNAME, Constants.PORT_NO)
            if (FTPReply.isPositiveCompletion(ftpClient.replyCode)) {
                Log.d(TAG, "FTP Client Connected")
            }
            ftpClient.login(Constants.USERNAME, Constants.PASSWORD)
            Log.d(TAG, "FTP Client Logged In")

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ftpClient
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        try {

            ftpClient!!.enterLocalPassiveMode()
            ftpClient!!.setFileType(FTP.BINARY_FILE_TYPE)

            stream = ftpClient!!.retrieveFileStream(model.imagePath)
            callback.onDataReady(stream)

        } catch (ex: Exception) {
            println("Error: " + ex.message)
            ex.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (ftpClient!!.isConnected) {
                try {
                    ftpClient!!.disconnect()
                } catch (exc: Exception) {
                }

            }
            ftpClient = null
        }
    }

    override fun cleanup() {
        if (stream != null) {
            try {
                stream!!.close()
            } catch (e: IOException) {
                // Ignore
            }

        }
        try {
            if (ftpClient!!.isConnected) {
                ftpClient!!.logout()
                ftpClient!!.disconnect()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    override fun cancel() {}

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }

    companion object {
        private val TAG = "FTPDataFetcher"
    }
}