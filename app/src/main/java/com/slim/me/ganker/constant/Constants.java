package com.slim.me.ganker.constant;

import android.os.Environment;

/**
 * Created by Slim on 2017/2/28.
 */
public class Constants {

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String APP_SDCARD_PATH = SDCARD_PATH + "/Ganker";

    public static final String PHOTO_SAVE_PATH = APP_SDCARD_PATH + "/photo";
}
