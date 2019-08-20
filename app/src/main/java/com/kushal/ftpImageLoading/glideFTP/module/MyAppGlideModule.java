package com.kushal.ftpImageLoading.glideFTP.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.kushal.ftpImageLoading.glideFTP.FTPModel;
import com.kushal.ftpImageLoading.glideFTP.FTPModelLoaderFactory;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NotNull Context context, @NotNull GlideBuilder builder) {
    }

    @Override
    public void registerComponents(@NotNull Context context, @NotNull Glide glide, @NotNull Registry registry) {
        registry.prepend(FTPModel.class, InputStream.class,
                new FTPModelLoaderFactory(context));
    }
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}