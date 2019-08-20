package com.kushal.ftpImageLoading.glideFTP

import android.content.Context

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory

import java.io.InputStream

class FTPModelLoaderFactory(context: Context) : ModelLoaderFactory<FTPModel, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<FTPModel, InputStream> {
        return FTPModelLoader()
    }

    override fun teardown() {
        // Do nothing.
    }
}