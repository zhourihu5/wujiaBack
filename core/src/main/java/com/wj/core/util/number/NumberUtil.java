package com.wj.core.util.number;

import java.util.Locale;

import com.wj.core.util.base.annotation.NotNull;
import com.wj.core.util.base.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

/**
 * 数字的工具类.
 * 
 * 1.原始类型数字与byte[]的双向转换(via Guava)
 * 
 * 2.判断字符串是否数字, 是否16进制字符串(via Common Lang)
 * 
 * 3.10机制/16进制字符串 与 原始类型数字/数字对象 的双向转换(参考Common Lang自写)
 */
public class NumberUtil {

	///////////// bytes[] 与原始类型数字转换 ///////

	public static byte[] toBytes(int value) {
		return Ints.toByteArray(value);
	}

	public static byte[] toBytes(long value) {
		return Longs.toByteArray(value);
	}

	/**
	 * copy from ElasticSearch Numbers
	 */
	public static byte[] toBytes(double val) {
		return toBytes(Double.doubleToRawLongBits(val));
	}

	public static int toInt(byte[] bytes) {
		return Ints.fromByteArray(bytes);
	}

	public static long toLong(byte[] bytes) {
		return Longs.fromByteArray(bytes);
	}

	/**
	 * copy from ElasticSearch Numbers
	 */
	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}

	/////// 判断字符串类型//////////
	/**
	 * 判断字符串是否合法数字
	 */
	public static boolean isNumber(@Nullable String str) {
		return NumberUtils.isCreatable(str);
	}

	/**
	 * 判断字符串是否16进制
	 */
	public static boolean isHexNumber(@Nullable String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}

		int index = value.startsWith("-") ? 1 : 0;
		return value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index);
	}

	/////////// 将字符串转化为原始类型数字/////////

	/**
	 * 将10进制的String转化为int.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static int toInt(@NotNull String str) {
		return Integer.parseInt(str);
	}

	/**
	 * 将10进制的String安全的转化为int.
	 * 
	 * 当str为空或非数字字符串时，返回default值.
	 */
	public static int toInt(@Nullable String str, int defaultValue) {
		return NumberUtils.toInt(str, defaultValue);
	}

	/**
	 * 将10进制的String安全的转化为long.
	 * 
	 * 当str或非数字字符串时抛NumberFormatException
	 */
	public static long toLong(@NotNull String str) {
		return Long.parseLong(str);
	}

	/**
	 * 将10进制的String安全的转化为long.
	 * 
	 * 当str为空或非数字字符串时，返回default值
	 */
	public static long toLong(@Nullable String str, long defaultValue) {
		return NumberUtils.toLong(str, defaultValue);
	}

	/**
	 * 将10进制的String安全的转化为double.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static double toDouble(@NotNull String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Double.parseDouble(str);
	}

	/**
	 * 将10进制的String安全的转化为double.
	 * 
	 * 当str为空或非数字字符串时，返回default值
	 */
	public static double toDouble(@Nullable String str, double defaultValue) {
		return NumberUtils.toDouble(str, defaultValue);
	}

	////////////// 10进制字符串 转换对象类型数字/////////////
	/**
	 * 将10进制的String安全的转化为Integer.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Integer toIntObject(@NotNull String str) {
		return Integer.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Integer.
	 * 
	 * 当str为空或非数字字符串时，返回default值
	 */
	public static Integer toIntObject(@Nullable String str, Integer defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将10进制的String安全的转化为Long.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Long toLongObject(@NotNull String str) {
		return Long.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Long.
	 * 
	 * 当str为空或非数字字符串时，返回default值
	 */
	public static Long toLongObject(@Nullable String str, Long defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Long.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将10进制的String安全的转化为Double.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Double toDoubleObject(@NotNull String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Double.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Long.
	 * 
	 * 当str为空或非数字字符串时，返回default值
	 */
	public static Double toDoubleObject(@Nullable String str, Double defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Double.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	//////////// 16进制 字符串转换为数字对象//////////

	/**
	 * 将16进制的String转化为Integer.
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Integer hexToIntObject(@NotNull String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Integer.decode(str);
	}

	/**
	 * 将16进制的String转化为Integer，出错时返回默认值.
	 */
	public static Integer hexToIntObject(@Nullable String str, Integer defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.decode(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将16进制的String转化为Long
	 * 
	 * 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Long hexToLongObject(@NotNull String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Long.decode(str);
	}

	/**
	 * 将16进制的String转化为Long，出错时返回默认值.
	 */
	public static Long hexToLongObject(@Nullable String str, Long defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Long.decode(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/////// toString ///////
	// 定义了原子类型与对象类型的参数，保证不会用错函数会导致额外AutoBoxing转换//

	public static String toString(int i) {
		return Integer.toString(i);
	}

	public static String toString(@NotNull Integer i) {
		return i.toString();
	}

	public static String toString(long l) {
		return Long.toString(l);
	}

	public static String toString(@NotNull Long l) {
		return l.toString();
	}

	public static String toString(double d) {
		return Double.toString(d);
	}

	public static String toString(@NotNull Double d) {
		return d.toString();
	}

	/**
	 * 输出格式化为小数后两位的double字符串
	 */
	public static String to2DigitString(double d) {
		return String.format(Locale.ROOT, "%.2f", d);
	}

	/////////// 杂项 ///////

	/**
	 * 安全的将小于Integer.MAX的long转为int，否则抛出IllegalArgumentException异常
	 */
	public static int toInt32(long x) {
		if ((int) x == x) {
			return (int) x;
		}
		throw new IllegalArgumentException("Int " + x + " out of range");
	}


	private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)

	/**
	 * 转化为弧度(rad)
	 * */
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}

	/**
	 * 基于余弦定理求两经纬度距离
	 * @param lon1 第一点的精度
	 * @param lat1 第一点的纬度
	 * @param lon2 第二点的精度
	 * @param lat2 第二点的纬度
	 * @return 返回的距离，单位m
	 * */
	public static double LantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);

		double radLon1 = rad(lon1);
		double radLon2 = rad(lon2);

		if (radLat1 < 0)
			radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
		if (radLat1 > 0)
			radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
		if (radLon1 < 0)
			radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
		if (radLat2 < 0)
			radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
		if (radLat2 > 0)
			radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
		if (radLon2 < 0)
			radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
		double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
		double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
		double z1 = EARTH_RADIUS * Math.cos(radLat1);

		double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
		double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
		double z2 = EARTH_RADIUS * Math.cos(radLat2);

		double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
		//余弦定理求夹角
		double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
		double dist = theta * EARTH_RADIUS;
		return dist;
	}


}
