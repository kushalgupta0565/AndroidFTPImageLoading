package com.kushal.ftpImageLoading.glideFTP

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey

import java.io.InputStream

class FTPModelLoader : ModelLoader<FTPModel, InputStream> {

    override fun buildLoadData(model: FTPModel, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(ObjectKey(model), FTPDataFetcher(model))
    }

    override fun handles(FTPModel: FTPModel): Boolean {
        return true
    }
}