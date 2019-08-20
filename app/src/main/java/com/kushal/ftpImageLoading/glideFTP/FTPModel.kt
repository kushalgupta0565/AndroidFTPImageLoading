package com.kushal.ftpImageLoading.glideFTP

class FTPModel(var imagePath: String) {

    override fun hashCode(): Int {
        return imagePath.hashCode()
    }

    override fun equals(obj: Any?): Boolean {
        return imagePath == (obj as FTPModel).imagePath
    }
}