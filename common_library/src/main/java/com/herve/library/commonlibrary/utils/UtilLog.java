package com.herve.library.commonlibrary.utils;

import android.util.Log;

/**
 * 打印日志
 *
 * @ClassName: UtilLog
 */
public class UtilLog {

    // 生产环境和调试环境区分
    public static boolean DEBUGGER = true;
    private final static boolean SAVETOFILE = false;

    public static void log(String tag, String msg) {
        if (DEBUGGER) {
            if (msg == null) {
                Log.i(tag, "null");
            } else {
                Log.i(tag, msg);
            }
            if (SAVETOFILE) {
                logToFile(tag, msg);
            }
        }

    }

    public static void error(String tag, String msg) {
        if (DEBUGGER) {
            if (msg == null) {
                Log.e(tag, "null");
            } else {
                Log.e(tag, msg);
            }
            if (SAVETOFILE) {
                logToFile(tag, msg);
            }
        }

    }

    /**
     * 往文件里面写log
     *
     * @param tag
     * @param msg
     */
    public static void logToFile(String tag, String msg) {
        //// TODO: 2016/8/8  错误日志按天存储，  （后续 需要把错误日志简单加密或混淆）
        //UtilFile.writeFile(UtilFile.DIR_TEST_LOG, tag + ";" + msg, true);
    }
}
