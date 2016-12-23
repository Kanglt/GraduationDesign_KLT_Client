/**     
*/
package lyu.klt.graduationdesign.module.dialog;

import com.lyu.graduationdesign_klt.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.Receiver.DownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.farment.VideoDisplayFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;

/**
 * @ClassName: VideoDownLoadDialog
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 康良涛
 * @date 2016年12月22日 下午7:50:27
 * 
 */
public class VideoDownLoadDialog {
	
	
	public static Dialog videoDownLoadDialog;
	public static void showVideoDownLoadDialog(final Context context,String videoName) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.activity_video_down_load, null);
		Button btn_exitDownload=(Button) viewDia.findViewById(R.id.btn_exitDownload);
		
		ProgressBar progressBar=(ProgressBar) viewDia.findViewById(R.id.downProgress);
		
		FileUtils.downloadVideo(context, UrlConstant.FILE_SERVICE_DOWNLOAD_VIDEO_URL+videoName,FileUtils.VIDEOSSAVEPATH, videoName,progressBar);
		
		
		btn_exitDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 注销下载线程中的Receiver
				 */
				Intent intent=new Intent(DownLoadCompleteReceiver.ACTION);
				intent.putExtra("isUnRegisterReceiver", true);
				context.sendBroadcast(intent);
				//关闭dialog
				videoDownLoadDialog.dismiss();
//				//播放下载好的视频
//				Message msg=new Message();
//				msg.obj="startVideo";
//				VideoDisplayFargmentActivity.handler.sendMessage(msg);
				
			}
		});
		
		videoDownLoadDialog = new Dialog(context, R.style.MyDialogStyle);
		videoDownLoadDialog.show();
		videoDownLoadDialog.setContentView(viewDia);
		//videoDownLoadDialog.setCancelable(false);// dialog弹出后会点击屏幕或物理返回键，dialog不消失
		videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}

}
