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
* @ClassName: MusicDataAPI 
* @Description: TODO(音乐数据) 
* @author 康良涛 
* @date 2016年12月26日 下午6:40:52 
*  
*/
public class MusicDataAPI {
	
	/**
	 * 
	* @Title: getMusicData_with_musicType 
	* @author 康良涛 
	* @Description: TODO(根据音乐类型获取音乐数据) 
	* @param @param context
	* @param @param musicType
	* @param @param abStringHttpResponseListener
	* @return void
	* @throws
	 */
	public static void getMusicData_with_musicType(Context context,String musicType,
			AbStringHttpResponseListener abStringHttpResponseListener){
		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("musicType", musicType);
			
			AbHttpUtil mAbHttpUtil = null;
			String url = UrlConstant.MUSICDATA_WITH_MUSICTYPE_URL;
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
