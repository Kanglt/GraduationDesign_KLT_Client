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
import android.widget.ImageView;
import android.widget.ProgressBar;

import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.Receiver.MusicDownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.Receiver.SystemApkDownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.Receiver.VideoDownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.fargment.VideoDisplayFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;

/**
 * @ClassName: DownLoadDialog
 * @Description: TODO(下载文件)
 * @author 康良涛
 * @date 2016年12月22日 下午7:50:27
 * 
 */
public class DownLoadDialog {
	
	
	public static Dialog dialog;
	/**
	 * 
	* @Title: showVideoDownLoadDialog 
	* @author 康良涛 
	* @Description: TODO(下载视频用到的dialog) 
	* @param @param context
	* @param @param videoName
	* @return void
	* @throws
	 */
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
				Intent intent=new Intent(VideoDownLoadCompleteReceiver.ACTION);
				intent.putExtra("isUnRegisterReceiver", true);
				context.sendBroadcast(intent);
				//关闭dialog
				dialog.dismiss();
//				//播放下载好的视频
//				Message msg=new Message();
//				msg.obj="startVideo";
//				VideoDisplayFargmentActivity.handler.sendMessage(msg);
				
			}
		});
		
		dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCancelable(false);// dialog弹出后会点击屏幕或物理返回键，dialog不消失
		//videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}
	
	/**
	 * 
	* @Title: showMusicDownLoadDialog 
	* @author 康良涛 
	* @Description: TODO(下载音乐用到的dialog) 
	* @param @param context
	* @param @param musicName
	* @param @param imageView
	* @return void
	* @throws
	 */
	public static void showMusicDownLoadDialog(final Context context,String musicName,ImageView imageView) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.activity_video_down_load, null);
		Button btn_exitDownload=(Button) viewDia.findViewById(R.id.btn_exitDownload);
		
		ProgressBar progressBar=(ProgressBar) viewDia.findViewById(R.id.downProgress);
		
		FileUtils.downloadVideo(context, UrlConstant.FILE_SERVICE_DOWNLOAD_MUSIC_URL+musicName,FileUtils.MUSICSSAVEPATH, musicName,progressBar,imageView);
		
		
		btn_exitDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 注销下载线程中的Receiver
				 */
				Intent intent=new Intent(MusicDownLoadCompleteReceiver.ACTION);
				intent.putExtra("isUnRegisterReceiver", true);
				context.sendBroadcast(intent);
				//关闭dialog
				dialog.dismiss();
//				//播放下载好的视频
//				Message msg=new Message();
//				msg.obj="startVideo";
//				VideoDisplayFargmentActivity.handler.sendMessage(msg);
				
			}
		});
		
		dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCancelable(false);// dialog弹出后会点击屏幕或物理返回键，dialog不消失
		//videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}
	
	public static String apkName="";
	public static void showNewVersionDownLoadDialog(final Context context,String apkName) {
		DownLoadDialog.apkName=apkName;
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.activity_video_down_load, null);
		Button btn_exitDownload=(Button) viewDia.findViewById(R.id.btn_exitDownload);
		
		ProgressBar progressBar=(ProgressBar) viewDia.findViewById(R.id.downProgress);
		
		FileUtils.downloadAPK(context, UrlConstant.FILE_SERVICE_DOWNLOAD_APK_URL+apkName,FileUtils.APKSAVEPATH, apkName,progressBar);
		
		
		btn_exitDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 注销下载线程中的Receiver
				 */
				Intent intent=new Intent(SystemApkDownLoadCompleteReceiver.ACTION);
				intent.putExtra("isUnRegisterReceiver", true);
				context.sendBroadcast(intent);
				//关闭dialog
				dialog.dismiss();
//				//播放下载好的视频
//				Message msg=new Message();
//				msg.obj="startVideo";
//				VideoDisplayFargmentActivity.handler.sendMessage(msg);
				
			}
		});
		
		dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCancelable(false);// dialog弹出后会点击屏幕或物理返回键，dialog不消失
		//videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}

}
