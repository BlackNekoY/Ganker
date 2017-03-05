package com.slim.me.ganker.constant;

import android.os.Environment;
import android.text.TextUtils;

/**
 * Created by Slim on 2017/2/28.
 */
public class Constants {

    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String APP_SDCARD_PATH = SDCARD_PATH + "/Ganker";

    public static final String PHOTO_SAVE_PATH = APP_SDCARD_PATH + "/photo";

    public static final String DATABASE_NAME = "ganker_db";

    public static class GankType {
        public static final String MEI_ZHI = "福利";
        public static final String ANDROID = "Android";
        public static final String IOS = "iOS";
        public static final String RELAX_VIDEO = "休息视频";
        public static final String EXPAND_RESOURCE = "拓展资源";
        public static final String FRONT_END = "前端";
        public static final String RECOMMAND = "瞎推荐";
        public static final String ALL = "all";

        public static boolean isVaildType (String type) {
            if(!TextUtils.isEmpty(type)) {
                switch (type) {
                    case MEI_ZHI:
                    case ANDROID:
                    case IOS:
                    case RELAX_VIDEO:
                    case EXPAND_RESOURCE:
                    case FRONT_END:
                    case RECOMMAND:
                    case ALL:
                        return true;
                }
            }
            return false;
        }
    }
}
