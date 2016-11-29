package lyu.klt.graduationdesign.util;

import java.util.Date;

import org.json.JSONObject;

import android.content.Context;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.util.AESTool;
import lyu.klt.frame.util.StringUtil;

/**   
 * 主要用来加解密客户端和服务端之间通信的数据
 * @Author   戴珍贵
 * @Version   1.0 
 * @CreateTime 2016年7月14日 下午5:22:49   
 */ 
public class DataUtils {
	
	/**
	 * @Title:  getRequestData
	 * @Description:    TODO(请求数据处理 ：添加时间戳以及加密数据)    
	 * @CreateBy: 戴珍贵 
	 * @CreateTime: 2016年7月14日 下午5:23:36
	 * @UpdateBy: 戴珍贵 
	 * @UpdateTime:  2016年7月14日 下午5:23:36    
	 * @param jsonObject
	 * @return  String 
	 * @throws 
	 */ 
	public static String getRequestData(Context context,JSONObject jsonObject){
		try {
			if(jsonObject == null)
				return null;
			//jsonObject.put("token", AbSharedUtil.getString(context, Constant.USERNAMETOKEN));
			jsonObject.put("requestTime", String.valueOf(new Date().getTime()));
			return AESTool.getInstance().encrypt(jsonObject.toString());
			//return jsonObject.toString();
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * @Title:  getResponseData
	 * @Description:    TODO(解密服务器返回的数据)    
	 * @CreateBy: 戴珍贵 
	 * @CreateTime: 2016年7月14日 下午5:41:00
	 * @UpdateBy: 戴珍贵 
	 * @UpdateTime:  2016年7月14日 下午5:41:00    
	 * @param context
	 * @param data
	 * @return  String 
	 * @throws 
	 */ 
	public static String getResponseData(Context context,String data){
		try {
			if(StringUtil.isEmpty(data))
				return null;
			return AESTool.getInstance().decrypt(data);
		} catch (Exception e) {
			return null;
		}
	}
	
}
