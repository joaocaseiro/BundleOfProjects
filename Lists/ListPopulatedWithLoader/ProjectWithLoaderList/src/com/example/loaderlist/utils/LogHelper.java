package com.example.loaderlist.utils;

import android.util.Log;

@SuppressWarnings("rawtypes")
public class LogHelper {
	private static final String topAppLevelDebugName = "Dom@Work";

	public static void logWithVerify(Class classCallingLog, int level, String message) {
		String tag = LogHelper.getTagFromClass(classCallingLog);
		if (Log.isLoggable(tag, level)) {
			log(tag, level, message);
		}
	}

	public static String getTagFromClass(Class classCallingLog) {
		String tag = topAppLevelDebugName + " - " + classCallingLog.getSimpleName();
		return tag;
	}

	public static void log(Class currentClass, int level, String message, boolean mVerbose) {
		if(mVerbose) {
			log(currentClass, level, message);
		}
	}
	
	public static void log(Class classCallingLog, int level, String message) {
		String tag = LogHelper.getTagFromClass(classCallingLog);
		log(tag, level, message);
	}

	private static void log(String tag, int level, String message) {
		switch (level) {
		case Log.VERBOSE:
			Log.v(tag, message);
			break;
		case Log.DEBUG:
			Log.d(tag, message);
			break;
		case Log.INFO:
			Log.i(tag, message);
			break;
		case Log.WARN:
			Log.w(tag, message);
			break;
		case Log.ERROR:
			Log.e(tag, message);
			break;
		}
	}


}
