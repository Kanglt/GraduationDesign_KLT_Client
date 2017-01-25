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
* @Description: TODO(获取推荐模块下训练模块的数据) 
* @author 康良涛 
* @date 2016年12月23日 下午2:28:13 
*  
*/
public class TrainingDataPAI {
	
	/**
	 * 
	* @Title: getTrainingData 
	* @author 康良涛 
	* @Description: TODO(根据标题获取训练数据) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getTrainingData(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.TRAININGDATA_URL;
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
	* @Title: getTotalTraining 
	* @author 康良涛 
	* @Description: TODO(获取全部训练) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getTotalTraining(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.TOTALTRAININGDATA_URL;
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
	* @Title: addTraining 
	* @author 康良涛 
	* @Description: TODO(添加训练) 
	* @param @param context
	* @param @param userId
	* @param @param category
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void addTraining(Context context,String userId,String category,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			jsonObject.put("category", category);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.ADDTRAININGDATA_URL;
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
	* @Title: getUserTrainingData 
	* @author 康良涛 
	* @Description: TODO(查询用户已添加训练) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getUserTrainingData(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.USERTRAININGDATA_URL;
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
	* @Title: deleteUserTraining 
	* @author 康良涛 
	* @Description: TODO(删除用户训练) 
	* @param @param context
	* @param @param userId
	* @param @param category
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void deleteUserTraining(Context context,String userId,String category,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			jsonObject.put("category", category);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.DELETETRAININGDATA_URL;
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
	* @Title: getUserTrainingTotalRecord 
	* @author 康良涛 
	* @Description: TODO(获取用户训练记录) 
	* @param @param context
	* @param @param userId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getUserTrainingTotalRecord(Context context,String userId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.USERTRAININGRECORD_URL;
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
	* @Title: addUserTrainingRecord 
	* @author 康良涛 
	* @Description: TODO(添加用户训练记录) 
	* @param @param context
	* @param @param userId
	* @param @param trainingDate
	* @param @param trainingCalories
	* @param @param trainingTime
	* @param @param category
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void addUserTrainingRecord(Context context,String userId,String trainingDate,String trainingCalories,String trainingTime,String category,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			jsonObject.put("userId", userId);
			jsonObject.put("category", category);
			jsonObject.put("trainingDate", trainingDate);
			jsonObject.put("trainingCalories", trainingCalories);
			jsonObject.put("trainingTime", trainingTime);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.ADDUSERTRAININGRECORD_URL;
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
	* @Title: queryRecommendedTraining 
	* @author 康良涛 
	* @Description: TODO(获取推荐训练) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getRecommendedTraining(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {
			
			
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.RECOMMENDEDTRAINING_URL;
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
	* @Title: queryAction 
	* @author 康良涛 
	* @Description: TODO(查询动作列表) 
	* @param @param context
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void queryAction(Context context,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYACTION_URL;
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
	* @Title: queryActionStep 
	* @author 康良涛 
	* @Description: TODO(查询动作步骤) 
	* @param @param context
	* @param @param actionId
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	
	public static void queryActionStep(Context context,String actionId,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("actionId", actionId);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.QUERYACTIONSTEP_URL;
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
