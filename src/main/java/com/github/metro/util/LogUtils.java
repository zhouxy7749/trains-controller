package com.github.metro.util;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static java.util.Calendar.getInstance;

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
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
				.format(getInstance().getTime());
	}

}
