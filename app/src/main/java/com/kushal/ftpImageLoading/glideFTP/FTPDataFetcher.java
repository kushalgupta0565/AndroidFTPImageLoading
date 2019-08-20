package com.kushal.ftpImageLoading.glideFTP;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.kushal.ftpImageLoading.utils.Constants;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FTPDataFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "FTPDataFetcher";
    private final FTPModel model;
    private InputStream stream;
    FTPClient ftpClient;


    FTPDataFetcher(FTPModel model) {
        this.model = model;
        this.ftpClient = getFtpClient();
    }

    private FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(15 * 1000);
        try {
            ftpClient.connect(Constants.INSTANCE.getNETWORK_HOSTNAME(), Constants.INSTANCE.getPORT_NO());
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                Log.d(TAG, "FTP Client Connected");
            }
            ftpClient.login(Constants.INSTANCE.getUSERNAME(), Constants.INSTANCE.getPASSWORD());
            Log.d(TAG, "FTP Client Logged In");

        } catch ( Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    @Override
    public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
        try {

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            stream = ftpClient.retrieveFileStream(model.getImagePath());
            callback.onDataReady(stream);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception exc) {
                }
            }
            ftpClient = null;
        }
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//    @Override
//    public String getId() {
//        return model.ftpPath;
//    }

    @Override
    public void cancel() {
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}