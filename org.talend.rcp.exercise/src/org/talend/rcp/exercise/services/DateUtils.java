package org.talend.rcp.exercise.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static final String DD_MM_YYYY_HH_MM_SS_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static String formatDate(long lastModified) {
		Date date = new Date(lastModified);
		SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_DATE_FORMAT);
		return formatter.format(date);
	}

}
