/**     
*/
package lyu.klt.graduationdesign.moudle.api;

import java.io.File;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import lyu.klt.frame.ab.http.AbHttpClient;
import lyu.klt.frame.ab.http.AbHttpUtil;
import lyu.klt.frame.ab.http.AbRequestParams;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;

/** 
* @ClassName: UserAPI 
* @Description: TODO(用户操作接口) 
* @author 康良涛 
* @date 2016年12月19日 下午12:24:45 
*  
*/
public class UserAPI {

	/**
	 * 
	* @Title: userLoginForMobile 
	* @author 康良涛 
	* @Description: TODO(用户登入接口) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void userLoginForMobile(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.USERLOGIN_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr",DataUtils.getRequestData(context, jsonObject));
			//params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}
		
	}
	
	/**
	 * 
	* @Title: userInformationForMobile 
	* @author 康良涛 
	* @Description: TODO(获取用户信息) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void userInformationForMobile(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.USERINFORMATION_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr",DataUtils.getRequestData(context, jsonObject));
			//params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}
		
	}
	
	/**
	 * 
	* @Title: updateUserInformationForMobile 
	* @author 康良涛 
	* @Description: TODO(修改用户信息) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void updateUserInformationForMobile(Context context,String userId, String userName, String userPassword,
			String userBirthday, String userPhoneNumble, String userSex, String userEmail,String userPhoto,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
	
		try {
			jsonObject.put("userId", userId);
			jsonObject.put("userName", userName);
			jsonObject.put("userPassword", userPassword);
			jsonObject.put("userBirthday", userBirthday);
			jsonObject.put("userPhoneNumble", userPhoneNumble);
			jsonObject.put("userSex", userSex);
			jsonObject.put("userEmail", userEmail);
			jsonObject.put("userPhoto", userPhoto);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.UPDATEUSERINFORMATION_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr",DataUtils.getRequestData(context, jsonObject));
			//params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);	
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}
		
	}
	
}
