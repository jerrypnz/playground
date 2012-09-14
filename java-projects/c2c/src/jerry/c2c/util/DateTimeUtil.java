package jerry.c2c.util;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateTimeUtil
{
	public static Timestamp getCurrentTimestamp()
	{
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}
}
