package com.kushal.ftpImageLoading.glideFTP.module

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.kushal.ftpImageLoading.glideFTP.FTPModel
import com.kushal.ftpImageLoading.glideFTP.FTPModelLoaderFactory

import java.io.InputStream

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {}

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(FTPModel::class.java, InputStream::class.java,
                FTPModelLoaderFactory(context))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}