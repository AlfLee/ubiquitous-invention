package com.telecom.utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * String的工具类
 * @author zj
 */
public class QString extends StringUtils
{
	public static boolean IsNullOrEmpty(String value)
	{
		return value == null || value.trim().contentEquals("");
	}

	public static List<String> clearNullOrEmpty(List<String> src)
	{
		List<String> rsp = new ArrayList<String>();
		for (int i = 0; i < src.size(); i++)
		{
			if (!QString.IsNullOrEmpty(src.get(i)))
			{
				rsp.add(src.get(i));
			}
		}
		return rsp;
	}

	public static byte[] charArrayTobyteArray(char[] data)
	{
		if (data == null) return null;
		String temp = new String(data);
		return temp.getBytes();
	}

	public static char[] byteArrayTocharArray(byte[] data)
	{
		if (data == null) return null;
		char[] rsp = new char[data.length];
		String temp = new String(data);
		temp.getChars(0, temp.length(), rsp, 0);
		return rsp;
	}

	public static Integer getInt(Object obj)
	{
		Integer rsp = null;
		if (obj != null)
		{
			if (obj instanceof Boolean && (Boolean) obj)
			{
				rsp = 1;
			}
			else if (obj instanceof Boolean && !(Boolean) obj)
			{
				rsp = 0;
			}
			else
			{
				rsp = Integer.parseInt(obj.toString());
			}
		}
		return rsp;
	}

	public static Long getLong(Object obj)
	{
		Long rsp = null;
		if (obj != null)
		{
			if (obj instanceof Boolean && (Boolean) obj)
			{
				rsp = 1L;
			}
			else if (obj instanceof Boolean && !(Boolean) obj)
			{
				rsp = 0L;
			}
			else
			{
				rsp = Long.parseLong(obj.toString());
			}
		}
		return rsp;
	}

	public static Float getFloat(Object obj)
	{
		Float rsp = null;
		if (obj != null)
		{
			if (obj instanceof Boolean && (Boolean) obj)
			{
				rsp = 1f;
			}
			else if (obj instanceof Boolean && !(Boolean) obj)
			{
				rsp = 0f;
			}
			else
			{
				rsp = Float.parseFloat(obj.toString());
			}
		}
		return rsp;
	}
	
	public static Boolean getBoolean(Object obj)
	{
		Boolean rsp = null;
		if (obj != null)
		{
			if (obj.toString().toLowerCase().equals("true"))
			{
				rsp = true;
			}
			else if (obj.toString().toLowerCase().equals("false"))
			{
				rsp = false;
			}
			else if (obj instanceof Boolean)
			{
				rsp = (Boolean) obj;
			}
			else
			{
				rsp = Double.parseDouble(obj.toString()) > 0 ? true : false;
			}
		}
		return rsp;
	}
	
	public static Date getDate(Object obj)
	{
		Date rsp = null;
		if (obj != null)
		{
			if (obj instanceof Date)
			{
				rsp = (Date)obj;
			}
			else
			{
				rsp = new Date(getLong(obj));
			}
		}
		return rsp;
	}
	
	public static String getDate(Object obj,String formatter)
	{
		String rsp = null;
		Date date=getDate(obj);
		if(date!=null)
		{
			rsp=QDate.formatter(date, formatter);
		}
		return rsp;
	}
}
