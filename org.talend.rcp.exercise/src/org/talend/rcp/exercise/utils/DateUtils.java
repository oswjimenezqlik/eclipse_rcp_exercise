package org.talend.rcp.exercise.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date formatting utilities
 */
public class DateUtils {

	private static final String DD_MM_YYYY_HH_MM_SS_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	/**
	 * Formats the unix epoch milis to readable date
	 * 
	 * @param epochMilis
	 * @return date formatted
	 */
	public static String formatDate(long epochMilis) {
		Date date = new Date(epochMilis);
		SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_DATE_FORMAT);
		return formatter.format(date);
	}

}
