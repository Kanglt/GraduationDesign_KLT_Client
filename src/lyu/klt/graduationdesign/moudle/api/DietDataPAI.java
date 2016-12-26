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
* @ClassName: TrainingDataPAI 
* @Description: TODO(获取推荐模块下饮食模块的数据) 
* @author 康良涛 
* @date 2016年12月23日 下午2:28:13 
*  
*/
public class DietDataPAI {
	
	/**
	 * 
	* @Title: getDietData 
	* @author 康良涛 
	* @Description: TODO(获取食品基本信息) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getDietData(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DIETDATA_URL;
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
	* @Title: getDietData_foodMateria 
	* @author 康良涛 
	* @Description: TODO(获取食品成分) 
	* @param @param context
	* @param @param dietName
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	
	public static void getDietData_foodMateria(Context context,String dietName,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dietName", dietName);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DIETDATA_DIETDATA_FOODMATERIA_URL;
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
	* @Title: getDietDetaile_step 
	* @author 康良涛 
	* @Description: TODO(获取食品制作步骤) 
	* @param @param context
	* @param @param dietName
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getDietDetaile_step(Context context,String dietName,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dietName", dietName);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DIETDATA_DIETDETAILE_STEP_URL;
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
	* @Title: getDietData_with_dinneTime_dietType 
	* @author 康良涛 
	* @Description: TODO(根据就餐时间与就餐类型返回食品数据) 
	* @param @param context
	* @param @param dinneTime
	* @param @param dinneType
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getDietData_with_dinneTime_dietType(Context context,String dinneTime,String dietType,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("dietType", dietType);
			jsonObject.put("dinneTime", dinneTime);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DIETDATA_WITH_DINNERTIME_DIETTYPE_URL;
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
