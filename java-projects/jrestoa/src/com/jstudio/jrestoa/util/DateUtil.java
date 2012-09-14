package com.jstudio.jrestoa.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil
{
	public static String toGMTString(Timestamp time)
	{
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.US);
		return format.format(time);
	}
}
