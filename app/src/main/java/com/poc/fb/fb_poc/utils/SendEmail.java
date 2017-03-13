package com.poc.fb.fb_poc.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.R.attr.path;

/**
 * Created by oferschonberger on 13/03/17.
 */
public class SendEmail {
    public void send(File dbFile, Activity activity) {
        File sdcardCopy = new File(Environment.getExternalStorageDirectory() + "/locations.db");
        try {
            copy(dbFile, sdcardCopy);
            Uri path = Uri.fromFile(sdcardCopy.getAbsoluteFile());
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent .setType("vnd.android.cursor.dir/email");
            emailIntent .putExtra(Intent.EXTRA_STREAM, path);
            emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Locations DB");
            activity.startActivity(Intent.createChooser(emailIntent , "Send email..."));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
