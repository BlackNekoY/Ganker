package com.slim.me.ganker.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Slim on 2017/2/27.
 */

public class BitmapUtil {

    public static final String TAG = "BitmapUtil";

    @Nullable
    public static File saveBitmap2File(@NonNull Bitmap bitmap, @NonNull String path, @NonNull String fileName)  {
        File parentFile = new File(path);
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        File outputFile = new File(parentFile, fileName);
        if(outputFile.exists()) {
            outputFile.delete();
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        }catch (FileNotFoundException e) {
            GLog.e(TAG, "FileNotFoundException:" + e.toString());
            return null;
        }catch (IOException e) {
            GLog.e(TAG, "IOException:" + e.toString());
            return null;
        }finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    GLog.e(TAG, "IOException:" + e.toString());
                    return null;
                }
            }
        }

        if(outputFile.length() <= 0) {
            GLog.e(TAG, "file length less than 0.");
            outputFile.delete();
            return null;
        }
        return outputFile;
    }
}
