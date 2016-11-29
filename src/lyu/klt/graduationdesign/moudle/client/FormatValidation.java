/*   
 * Copyright (c) 2015-2020 FuJianZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */package lyu.klt.graduationdesign.moudle.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用来进行格式验证
 * 
 * @Author 康良涛
 * @Version 1.0
 * @CreateTime 2016-8-2 下午5:54:06
 */
public class FormatValidation {

	/**
	 * 判断邮箱是否合法
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 
	 * @Title: isMobileNO
	 * @Description: TODO(判断手机号码格式是否正确)
	 * @CreateBy: 康良涛
	 * @CreateTime: 2016-8-2 下午5:56:38
	 * @UpdateBy: 康良涛
	 * @UpdateTime: 2016-8-2 下午5:56:38
	 * @param mobiles
	 * @return boolean
	 * @throws
	 */
	public static boolean isMobileNO(String mobiles) {
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//		Pattern p = Pattern
//				.compile("^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,1,2,5,6,7,8,9]\\d{8}$)|(14\\d{9})$)|(17\\d{9}$)");
		Pattern p = Pattern
				.compile("^((13\\d{9}$)|(15\\d{9}$)|(18\\d{9}$)|(14\\d{9})$)|(17\\d{9}$)");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	String str = "^[1-9][0-9]{5}$";

	/**
	 * 判断邮编
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isZipNO(String zipString) {
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}
}
