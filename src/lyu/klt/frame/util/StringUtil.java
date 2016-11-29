package lyu.klt.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

public class StringUtil {

	public static final String URL_REG_EXPRESSION = "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*";
	public static final String EMAIL_REG_EXPRESSION = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

	public static boolean isUrl(String s) {
		if (s == null) {
			return false;
		}
		return Pattern.matches(URL_REG_EXPRESSION, s);
	}

	public static boolean isEmail(String s) {
		if (s == null) {
			return true;
		}
		return Pattern.matches(EMAIL_REG_EXPRESSION, s);
	}

//	public static boolean isBlank(String s) {
//		if (s == null) {
//			return true;
//		}
//		if (s.length() <= 0)
//			return true;
//		if (s.equalsIgnoreCase("null"))
//			return true;
//		return Pattern.matches("\\s*", s);
//	}

	public static String join(String spliter, Object[] arr) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		if (spliter == null) {
			spliter = "";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {
				break;
			}
			if (arr[i] == null) {
				continue;
			}
			builder.append(arr[i].toString());
			builder.append(spliter);
		}
		return builder.toString();
	}

	public static String fromFile(File f) throws IOException {
		InputStream is = new FileInputStream(f);
		byte[] bs = new byte[is.available()];
		is.read(bs);
		is.close();
		return new String(bs);
	}

	public static boolean isEmpty(String value) {
		if(value == null)
			return true;
		String str = value.trim().toLowerCase();
		if(str.length() == 0)
			return true;
		
		if("null".equals(str))
			return true;
		if("[]".equals(value))
			return true;
		return false;
//		return value == null || "".equals(value) || "null".equals(value)
//				|| "[]".equals(value) ? true : false;
	}

	/*
	 * public static String formatDataText(Object data){ if(data != null){
	 * DecimalFormat df = new DecimalFormat("###############0.00");//
	 * 16位整数位，两小数�? String temp = df.format(d); //BigDecimal bd = new
	 * BigDecimal(String.valueOf(data)); //return bd.toPlainString(); return
	 * NumberUtil.FormatString(String.valueOf(data)); //return
	 * String.valueOf(data); }else{ return ""; } }
	 */

	public static SpannableString formatPriceText(String str1, String str2) {
		SpannableString sp = new SpannableString(str1 + str2);
		sp.setSpan(new RelativeSizeSpan(0.7f), 0, str1.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.0f), str1.length(), str1.length()
				+ str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Test if the given String ends with the specified suffix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param suffix
	 *            the suffix to look for
	 * @see java.lang.String#endsWith
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length())
				.toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean equalWithIgnoreCase(String str, String prefix) {

		if (str == null || prefix == null) {
			return false;
		}
		if (str.equalsIgnoreCase(prefix)) {
			return true;
		}
		if (str.length() != prefix.length()) {
			return false;
		}
		String lcStr = str.toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equalsIgnoreCase(lcPrefix);

	}

	public static boolean equalWith(String str, String prefix) {

		if (str == null || prefix == null) {
			return false;
		}
		if (str.equals(prefix)) {
			return true;
		}
		if (str.length() != prefix.length()) {
			return false;
		}
		String lcStr = str.trim();
		String lcPrefix = prefix.trim();
		return lcStr.equals(lcPrefix);

	}

	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 获得服务器的返回直 去除头尾2个""符号
	 * @param content
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonContent(String content) throws JSONException{
		String json = content.substring(1, content.length()-1).replace("\\", "");
		return new JSONObject(json);
		
	}
	public static String FormatDouble(double data) {
		BigDecimal bigDecimal = new BigDecimal(data);
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		String result = bigDecimal.toString();
		return formateRate(result);
	}

	// 保留两位
	public static String formateRate(String rateStr) {
		if (rateStr.indexOf(".") != -1) {
			// 获取小数点的位置
			int num = 0;
			num = rateStr.indexOf(".");

			// 获取小数点后面的数字 是否有两位 不足两位补足两位
			String dianAfter = rateStr.substring(0, num + 1);
			String afterData = rateStr.replace(dianAfter, "");
			if (afterData.length() < 2) {
				afterData = afterData + "0";
			} else {
				afterData = afterData;
			}
			return rateStr.substring(0, num) + "." + afterData.substring(0, 2);
		} else {
			return rateStr;
			/*
			 * if(rateStr == "1"){ return "100"; }else{ return rateStr; }
			 */
		}
	}
}
