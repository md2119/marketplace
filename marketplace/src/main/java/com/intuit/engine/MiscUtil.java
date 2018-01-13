package com.intuit.engine;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MiscUtil {
	
	public static String getISOStringForDate(LocalDateTime date) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");//("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		return dateFormat.format(date);
	}

}
