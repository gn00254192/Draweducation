package com.practice.draw.draweducation;

import java.io.File;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

public class SingleMediaScanner implements MediaScannerConnectionClient {
    private MediaScannerConnection mMSC;
    private File mFile;

    public SingleMediaScanner(Context context, File f) {
        mFile = f;
        mMSC =new MediaScannerConnection(context, this);
        mMSC.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        mMSC.scanFile(mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        mMSC.disconnect();
    }
}