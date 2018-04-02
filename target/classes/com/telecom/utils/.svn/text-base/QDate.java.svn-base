/**
 * 
 */
package com.telecom.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期类
 * @author zj
 */
public class QDate
{
	public static Date NOW()
	{
		return new Date();
	}

	private Calendar calendar = Calendar.getInstance();

	public static QDate getInstance(Date _d)
	{
		return new QDate(_d);
	}

	public static QDate getInstance()
	{
		return new QDate();
	}

	public QDate()
	{
		calendar.setTime(new Date());
	}

	public QDate(Date _d)
	{
		calendar.setTime(_d);
	}

	public QDate addSeconds(int seconds)
	{
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + seconds);
		return this;
	}

	public QDate addHours(int hours)
	{
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				+ hours);
		return this;
	}

	public QDate addDay(int days)
	{
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ days);
		return this;
	}

	public QDate addWeek(int weeks)
	{
		calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK)
				+ weeks);
		return this;
	}

	public QDate addMonths(int months)
	{
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);
		return this;
	}

	public QDate addYears(int years)
	{
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);
		return this;
	}

	public static long getSeparateYears(Date begin, Date end)
	{
		return getSeparateMonths(begin, end) / 12;
	}

	public static long getSeparateMonths(Date begin, Date end)
	{
		return getSeparateDays(begin, end) / 30;
	}

	public static long getSeparateDays(Date begin, Date end)
	{
		return getSeparateSeconds(begin, end) / (60 * 60 * 24);
	}

	public static long getSeparateSeconds(Date begin, Date end)
	{
		return getSeparateMilisSeconds(begin, end) / 1000;
	}

	public static long getSeparateMilisSeconds(Date begin, Date end)
	{
		Calendar bc = Calendar.getInstance();
		bc.setTime(begin);
		Calendar ec = Calendar.getInstance();
		ec.setTime(end);
		return (ec.getTimeInMillis() - bc.getTimeInMillis());
	}

	public Date getValue()
	{
		return calendar.getTime();
	}

	public static Date Parser(String date, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			return sdf.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String formatter(Long date, String pattern)
	{
		if(date==null)
			return QString.EMPTY;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(date));
	}

	public static String formatter(Date date, String pattern)
	{
		if(date==null)
			return QString.EMPTY;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
