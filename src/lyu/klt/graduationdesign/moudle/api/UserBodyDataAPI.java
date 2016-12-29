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
 * @ClassName: UserBodyDataAPI
 * @Description: TODO(用户身体数据API)
 * @author 康良涛
 * @date 2016年12月28日 下午8:49:53
 * 
 */
public class UserBodyDataAPI {

	/**
	 * 
	 * @Title: getUserBodyData @author 康良涛 @Description:
	 * TODO(获取用户身体数据) @param @param context @param @param
	 * abStringHttpResponseListener @return void @throws
	 */
	public static void getUserBodyData(Context context, String userId, String dataType,
			AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);
			jsonObject.put("dataType", dataType);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.GETUSERBODYDATA_URL;
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
	 * @Title: addUserBodyData @author 康良涛 @Description:
	 * TODO(添加用户身体数据) @param @param context @param @param
	 * abStringHttpResponseListener @return void @throws
	 */
	public static void addUserBodyData(Context context, String userId, String dataType, String data, String height,
			String weight, String date, AbStringHttpResponseListener abStringHttpResponseListener) {
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("userId", userId);
			jsonObject.put("dataType", dataType);
			jsonObject.put("data", data);
			jsonObject.put("height", height);
			jsonObject.put("weight", weight);
			jsonObject.put("date", date);

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.ADDUSERBODYDATA_URL;
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
