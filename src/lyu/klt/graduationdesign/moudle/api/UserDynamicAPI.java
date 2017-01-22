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
	
	/**
	 * 
	* @Title: queryHotDynamic 
	* @author 康良涛 
	* @Description: TODO(查询热门动态) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
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
	
	/**
	 * 
	* @Title: updateUserDynamicThumbUpNum 
	* @author 康良涛 
	* @Description: TODO(更新点赞数) 
	* @param @param context
	* @param @param userId
	* @param @param dynamicId
	* @param @param type（0表示加 1表示减）
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void updateUserDynamicThumbUpNum(Context context,String userId,String dynamicId,String type,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);
			jsonObject.put("dynamicId", dynamicId);
			jsonObject.put("type", type);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.UPDATEUSERDYNAMICTHUMBUPNUM_URL;
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
	* @Title: deleteDynamicComments 
	* @author 康良涛 
	* @Description: TODO(删除动态评论) 
	* @param @param context
	* @param @param userId
	* @param @param dynamicId
	* @param @param type
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void deleteDynamicComments(Context context,String dynamicId,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dynamicId", dynamicId);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DELETEDYNAMICCOMMENTS_URL;
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
	* @Title: queryDynamicComments 
	* @author 康良涛 
	* @Description: TODO(查询动态评论) 
	* @param @param context
	* @param @param userId
	* @param @param dynamicId
	* @param @param type
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void queryDynamicComments(Context context,String dynamicId,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dynamicId", dynamicId);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYDYNAMICCOMMENTS_URL;
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
	* @Title: addDynamicComments 
	* @author 康良涛 
	* @Description: TODO(添加动态评论) 
	* @param @param context
	* @param @param dynamicId
	* @param @param commentsUserId
	* @param @param commentsText
	* @param @param replyId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void addDynamicComments(Context context,String dynamicId,String commentsUserId,String commentsText,String replyId,String replyName,String commentsUserName,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dynamicId", dynamicId);
			jsonObject.put("commentsUserId", commentsUserId);
			jsonObject.put("commentsText", commentsText);
			jsonObject.put("replyId", replyId);
			jsonObject.put("replyName", replyName);
			jsonObject.put("commentsUserName", commentsUserName);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.ADDDYNAMICCOMMENTS_URL;
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
