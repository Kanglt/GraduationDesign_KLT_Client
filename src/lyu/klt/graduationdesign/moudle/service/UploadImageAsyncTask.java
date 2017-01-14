/**     
*/
package lyu.klt.graduationdesign.moudle.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import lyu.klt.graduationdesign.util.UploadUtil;

/** 
* @ClassName: uploadImageAsyncTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2017年1月14日 下午4:55:51 
*  
*/
public class UploadImageAsyncTask extends AsyncTask<Void, Void, Boolean> {

	private Context context;
	private Bitmap myBitmap;
	private String photoName;
	
	public UploadImageAsyncTask(Context context, Bitmap myBitmap,String photoName){
		this.context=context;
		this.myBitmap=myBitmap;
		this.photoName=photoName;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if (!UploadUtil.addDynamicImage(context, myBitmap, photoName)) {
			return true;
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
}
