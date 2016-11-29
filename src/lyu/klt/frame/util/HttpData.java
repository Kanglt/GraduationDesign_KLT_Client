/*   
 * Copyright (c) 2015-2020 FJZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */ 
package lyu.klt.frame.util;

import java.io.Serializable;

/**   
 * This class is used for ...   
 * @author   Dai
 * @version   1.0 
 * @datetime 2015年12月12日 上午9:04:06   
 */
public class HttpData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */  
	private static final long serialVersionUID = 2467930828258677345L;
	private String data;
	private String code;
	private String msg;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
