/**     
*/
package lyu.klt.graduationdesign.module.Receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.module.fargment.FitnessFargmentActivity;

/** 
* @ClassName: DownLoadCompleteReceiver 
* @Description: TODO(下载文件用到的Receiver) 
* @author 康良涛 
* @date 2016年12月22日 下午7:01:03 
*  
*/
public class VideoDownLoadCompleteReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	static final public String ACTION = "DownLoadCompleteReceiver"; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			Toast.makeText(context, "任务下载完成！", Toast.LENGTH_SHORT).show();
			DownLoadDialog.dialog.dismiss();
			FitnessFargmentActivity.isRefresh=true;
			Message msg=new Message();
			msg.obj="refresh";
			FitnessFargmentActivity.handler.sendMessage(msg);
			
		}else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
			Toast.makeText(context, "这里不能点哦~", Toast.LENGTH_SHORT).show();
		}
		
		if(intent.getBooleanExtra("isUnRegisterReceiver", false)){
		//	Toast.makeText(context, "注销了DownLoadCompleteReceiver", Toast.LENGTH_SHORT).show();
			context.unregisterReceiver(this);
		}
	}

}
