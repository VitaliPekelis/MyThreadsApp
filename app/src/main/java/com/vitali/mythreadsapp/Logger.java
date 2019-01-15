package com.vitali.mythreadsapp;

import android.util.Log;

public class Logger {

        private static final String TAG = Logger.class.getSimpleName();

        public static void logDebug()
        {
            Log.d(TAG, generateLogText(""));
        }

        public static void logDebug(String tag, String logText)
        {
            Log.d(tag, generateLogText(logText));
        }

        public static void logError(String tag, String logText)
        {
            Log.e(tag, generateLogText(logText));
        }
        public static void logError(String tag, String logText, Throwable tr)
        {
            Log.w(tag, generateLogText(logText), tr);
        }

        public static void logInfo(String tag, String logText)
        {
            Log.i(tag, generateLogText(logText));
        }

        private static String generateLogText(String logText)
        {
            String response = logText;

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            if(stackTrace !=null && stackTrace.length > 4)
            {
                StackTraceElement element    = stackTrace[4];
                String fullClassName = element.getClassName();
                String className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1); //no package

                //add class and method data to logText
                StringBuilder builder = new StringBuilder("T:");
                builder.append(Thread.currentThread().getId())
                        .append(" | ")
                        .append(className)
                        .append(".")
                        .append(element.getMethodName())
                        .append("()")
                        .append(" | ")
                        .append(logText);

                response = builder.toString();
            }

            return response;
        }



}
