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
* @ClassName: ActivityAPI 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2017年1月25日 下午12:28:44 
*  
*/
public class ActivityAPI {

	/**
	 * 
	* @Title: queryActivity 
	* @author 康良涛 
	* @Description: TODO(查询活动) 
	* @param @param context
	* @param @param activityType
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void queryActivity(Context context,String activityType,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("activityType", activityType);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYACTIVITY_URL;
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
