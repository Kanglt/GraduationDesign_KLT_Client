/**     
*/
package lyu.klt.graduationdesign.moudle.api;

import org.json.JSONObject;

import android.content.Context;
import lyu.klt.frame.ab.http.AbHttpUtil;
import lyu.klt.frame.ab.http.AbRequestParams;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;

/** 
* @ClassName: UserDynamicAPI 
* @Description: TODO(用户动态相关接口) 
* @author 康良涛 
* @date 2017年1月14日 下午3:18:21 
*  
*/
public class UserDynamicAPI {
	
	
	/**
	 * 
	* @Title: addUserDynamic 
	* @author 康良涛 
	* @Description: TODO(用户发布动态) 
	* @param @param context
	* @param @param userId
	* @param @param dataType
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void addUserDynamic(Context context, String userId, String dynamicDate, String dynamicText, String dynamicImage,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);
			jsonObject.put("dynamicDate", dynamicDate);
			jsonObject.put("dynamicText", dynamicText);
			jsonObject.put("dynamicImage", dynamicImage);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.ADDUSERDYNAMIC_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr", DataUtils.getRequestData(context, jsonObject));
			// params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}

	}
	
	/**
	 * 
	* @Title: queryUserPersonalDynamic 
	* @author 康良涛 
	* @Description: TODO(查询用户个人动态) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void queryUserPersonalDynamic(Context context, String userId,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYUSERPERSONALDYNAMIC_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr", DataUtils.getRequestData(context, jsonObject));
			// params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}

	}
	
	/**
	 * 
	* @Title: queryUserFocusDynamic 
	* @author 康良涛 
	* @Description: TODO(查询用户关注动态) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void queryUserFocusDynamic(Context context, String userId,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYUSERFOCUSDYNAMIC_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr", DataUtils.getRequestData(context, jsonObject));
			// params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}

	}
	
	public static void queryHotDynamic(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYHOTDYNAMIC_URL;
			// 绑定参数
			AbRequestParams params = new AbRequestParams();
			params.put("jsonDataStr", DataUtils.getRequestData(context, jsonObject));
			// params.put("jsonDataStr",jsonObject.toString());
			mAbHttpUtil = AbHttpUtil.getInstance(context);
			mAbHttpUtil.setTimeout(10000);
			mAbHttpUtil.post(url, params, abStringHttpResponseListener);
		} catch (Exception e) {
			AbLogUtil.e(context, e.getMessage());
		}

	}
}
