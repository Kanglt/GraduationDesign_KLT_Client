/**     
*/
package lyu.klt.graduationdesign.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;

/**
 * @ClassName: UploadUtil
 * @Description: TODO(上传文件到服务器)
 * @author 康良涛
 * @date 2016年12月20日 下午9:27:12
 * 
 */
public class UploadUtil {


	/**
	 * 
	* @Title: reg 
	* @author 康良涛 
	* @Description: TODO(上传用户头像) 
	* @param @param cont
	* @param @param photodata
	* @param @param photoName 需要加上后缀名
	* @return void
	* @throws
	 */
	public static boolean updateUserPhoto(final Context cont, Bitmap photodata,String photoName) {
		try {

			String url = UrlConstant.UPDATEUSERPHOTO_URL;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
			photodata.compress(Bitmap.CompressFormat.PNG, 100, baos);
			baos.close();
			byte[] buffer = baos.toByteArray();
			System.out.println("图片的大小：" + buffer.length);

			// 将图片的字节流数据加密成base64字符输出
			String photo = Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);

			// photo=URLEncoder.encode(photo,"UTF-8");
			RequestParams params = new RequestParams();
			params.put("photo", photo);
			params.put("photoName", photoName);// 传输的字符数据

			AsyncHttpClient client = new AsyncHttpClient();
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, cz.msebera.android.httpclient.Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
//					Toast.makeText(cont, "头像上传成功!", 0)
//                  	 .show(); 
					AbSharedUtil.putBoolean(cont, Constant.ISUPDATEUSERPHOTO, true);
				}

				@Override
				public void onSuccess(int arg0, cz.msebera.android.httpclient.Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
//					Toast.makeText(cont, "头像上传失败!", 0)
//                 	 .show(); 
				}

			});
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	

}
