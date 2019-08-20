package com.kushal.ftpImageLoading.utils

import android.util.Log
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import rx.Observable

object AppUtil {

    fun getFTPObservable(): Observable<Any?> {
        return Observable.defer { Observable.just(connectToFTP()) }
    }

    private fun connectToFTP(): Any? {
        val ftpClient = FTPClient()
        ftpClient.setConnectTimeout(15 * 1000)
        try {
            ftpClient.connect(Constants.NETWORK_HOSTNAME, Constants.PORT_NO)
            if (FTPReply.isPositiveCompletion(ftpClient.replyCode)) {
                Log.d("FTP Client", "Connected")
            }
            ftpClient.login(Constants.USERNAME, Constants.PASSWORD)
            Log.d("FTP Client", "Logged In")

            if (ftpClient.isConnected && ftpClient.isAvailable) {
                val ftpDirectoriesIterator = ftpClient.listDirectories(Constants.NETWORK_FOLDER_NAME).iterator()
                val dataMap = HashMap<String, ArrayList<String>>()

                ftpDirectoriesIterator.forEach { ftpDirectories ->
                    run {
                        if (ftpDirectories.isDirectory) {
                            val directoryName = ftpDirectories.name
                            val ftpFilesIterator = ftpClient.listFiles(Constants.NETWORK_FOLDER_NAME + directoryName).iterator()
                            val arrayList = ArrayList<String>()
                            ftpFilesIterator.forEach { ftpFile ->
                                kotlin.run {
                                    if (ftpFile.isFile) {
                                        val fileName = ftpFile.name
                                        if (fileName.contains("png") || fileName.contains("jpg") || fileName.contains("jpeg")) {
                                            arrayList.add(fileName)
                                        }
                                    }
                                }
                            }
                            if (arrayList.size > 0)
                                dataMap.put(directoryName, arrayList)
                        }
                    }
                }
                return dataMap
            } else {
                return "FTP is not Connected/Available"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return e.message
        }
    }
}
