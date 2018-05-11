package com.github.metro.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class LogUtils {
	private static LinkedList<String> logs = null;

	private LogUtils() {
	}

	public static LinkedList<String> getLogs() {
		if (logs == null) {
			logs = new LinkedList<>();
		}
		return logs;
	}

	public static String currentTime() {
		String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ")
				.format(Calendar.getInstance().getTime());
		return time;
	}

}
