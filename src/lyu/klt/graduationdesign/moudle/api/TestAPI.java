/**     
*/
package lyu.klt.graduationdesign.moudle.api;



import java.util.Date;

import org.json.JSONObject;

import android.content.Context;
import lyu.klt.frame.ab.http.AbHttpUtil;
import lyu.klt.frame.ab.http.AbRequestParams;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;

/** 
* @ClassName: TestAPI 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2016年11月27日 上午10:31:52 
*  
*/
public class TestAPI {

	private static final String TAG = TestAPI.class
			.getSimpleName();
	
	
	
	/**
	 * 
	* @Title: testWebConnectionForMoblei 
	* @author 康良涛 
	* @Description: TODO(服务器连接测试API) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void testForMobile(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.TESTURL;
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
