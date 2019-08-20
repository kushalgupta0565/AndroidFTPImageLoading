package com.kushal.ftpImageLoading.glideFTP;

public class FTPModel {

    String imageFile;

    public FTPModel(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public int hashCode() {
        return imageFile.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return imageFile.equals(((FTPModel) obj).imageFile);
    }

    public String getImagePath() {
        return imageFile;
    }

    public void setImagePath(String imageFile) {
        this.imageFile = imageFile;
    }
}