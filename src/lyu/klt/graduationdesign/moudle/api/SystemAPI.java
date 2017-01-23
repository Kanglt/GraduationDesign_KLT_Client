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
* @ClassName: SystemAPI 
* @Description: TODO(系统相关接口) 
* @author 康良涛 
* @date 2017年1月23日 下午10:43:10 
*  
*/
public class SystemAPI {

	/**
	 * 
	* @Title: querySystemVersionInfomation 
	* @author 康良涛 
	* @Description: TODO(查询系统版本信息) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void querySystemVersionInfomation(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("systemPlatform", "android");
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYSYSTEMVERSIONINFOMATION_URL;
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
