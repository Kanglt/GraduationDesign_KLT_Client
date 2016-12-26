package lyu.klt.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import lyu.klt.graduationdesign.module.Receiver.VideoDownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.Receiver.MusicDownLoadCompleteReceiver;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

public class FileUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory() + "/focus/";// formats
	public static String VIDEOSSAVEPATH = "focus/videos";
	public static String MUSICSSAVEPATH = "focus/musics";

	private final static String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" }, { ".doc", "application/msword" },
			{ ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" }, { ".html", "text/html" },
			{ ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
			{ ".png", "image/png" }, { ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/x-zip-compressed" },
			{ "", "*/*" } };

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	public static void openFile(Context context, File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		context.startActivity(intent);

	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".jpg");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * 计算文件夹大小
	 */
	public static final long getSize(File dir) {
		long retSize = 0;
		if (dir == null) {
			return retSize;
		}
		if (dir.isFile()) {
			return dir.length();
		}
		File[] entries = dir.listFiles();

		if (entries == null)
			return retSize;
		int count = entries.length;
		for (int i = 0; i < count; i++) {
			if (entries[i].isDirectory()) {
				retSize += getSize(entries[i]);
			} else {
				retSize += entries[i].length();
			}
		}
		return retSize;
	}

	public static boolean isSDCardAvaliable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param descFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			return false;
		} else if (!srcFile.isFile()) {
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * bitmap转成byte
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] convertIconToByte(Bitmap bitmap) {
		int bytes = bitmap.getByteCount();

		ByteBuffer buf = ByteBuffer.allocate(bytes);
		bitmap.copyPixelsToBuffer(buf);

		byte[] byteArray = buf.array();
		return byteArray;

	}

	/**
	 * byte转成bitmap
	 * 
	 * @param st
	 */
	public static Bitmap convertByteToIcon(byte[] bt) {
		// use Bitmap.Config.ARGB_8888 instead of type is OK
		// Bitmap stitchBmp = Bitmap.createBitmap(width, height, type);
		//
		// stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(bt));
		// return stitchBmp;

		if (bt.length != 0) {
			return BitmapFactory.decodeByteArray(bt, 0, bt.length);
		} else {
			return null;
		}
		//
	}

	/**
	 * 
	 * @Title: downloadVideo @author 康良涛 @Description: TODO(下载视频) @param @param
	 * url @param @param savePath @return void @throws
	 */
	private static DownloadManager downManager;
	private static long idPro;
	private static ProgressBar progressBar;

	public static void downloadVideo(Context context, String url, String savePath, String videoName,
			ProgressBar progressBar) {
		FileUtils.progressBar = progressBar;
		VideoDownLoadCompleteReceiver receiver;
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
		filter.addAction(VideoDownLoadCompleteReceiver.ACTION);
		receiver = new VideoDownLoadCompleteReceiver();
		context.registerReceiver(receiver, filter);

		downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		// 设置在什么网络情况下进行下载
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		// 设置通知栏标题
		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
		request.setTitle("下载");
		request.setDescription("文件正在下载");
		request.setAllowedOverRoaming(false);
		/**
		 * setDestinationInExternalPublicDir方法 
		 * 这个方法也是用来“制定”一个路径的，这个路径的特性类似于getExternalStoragePublicDirectory(String))
		 * getExternalStoragePublicDirectory(String)) 这个方法的返回值
		 * 是一个文件夹，这个文件夹是被创建在你的SD卡根目录的（mnt/sdcard/） 这个文件夹中的内容其他程序都是可以访问的（没有访问控制）
		 * 当你的应用程序卸载的时候，这个文件夹中的内容将不会丢失。
		 */
		// 设置文件存放目录
		request.setDestinationInExternalPublicDir(savePath, videoName);
		// 开启下载任务
		idPro = downManager.enqueue(request);

		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// queryTaskByIdandUpdateView(idPro);
				DownloadManager.Query query = new DownloadManager.Query();
				query.setFilterById(idPro);
				Cursor cursor = downManager.query(query);
				String size = "0";
				String sizeTotal = "0";
				if (cursor.moveToNext()) {
					size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
					sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				}
				cursor.close();
				FileUtils.progressBar.setMax(Integer.valueOf(sizeTotal));
				FileUtils.progressBar.setProgress(Integer.valueOf(size));
				//ses.shutdown();
				if(Integer.valueOf(size)>=Integer.valueOf(sizeTotal)){
					//VideoDownLoadDialog.videoDownLoadDialog.dismiss();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

	}
	
	
	/**
	 * 
	 * @Title: downloadVideo @author 康良涛 @Description: TODO(下载视频) @param @param
	 * url @param @param savePath @return void @throws
	 */

	public static ImageView imageview;
	public static void downloadVideo(Context context, String url, String savePath, String videoName,
			ProgressBar progressBar,ImageView imageview) {
		FileUtils.progressBar = progressBar;
		FileUtils.imageview=imageview;
		MusicDownLoadCompleteReceiver receiver;
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
		filter.addAction(VideoDownLoadCompleteReceiver.ACTION);
		receiver = new MusicDownLoadCompleteReceiver();
		context.registerReceiver(receiver, filter);

		downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		// 设置在什么网络情况下进行下载
		request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		// 设置通知栏标题
		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
		request.setTitle("下载");
		request.setDescription("文件正在下载");
		request.setAllowedOverRoaming(false);
		/**
		 * setDestinationInExternalPublicDir方法 
		 * 这个方法也是用来“制定”一个路径的，这个路径的特性类似于getExternalStoragePublicDirectory(String))
		 * getExternalStoragePublicDirectory(String)) 这个方法的返回值
		 * 是一个文件夹，这个文件夹是被创建在你的SD卡根目录的（mnt/sdcard/） 这个文件夹中的内容其他程序都是可以访问的（没有访问控制）
		 * 当你的应用程序卸载的时候，这个文件夹中的内容将不会丢失。
		 */
		// 设置文件存放目录
		request.setDestinationInExternalPublicDir(savePath, videoName);
		// 开启下载任务
		idPro = downManager.enqueue(request);

		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// queryTaskByIdandUpdateView(idPro);
				DownloadManager.Query query = new DownloadManager.Query();
				query.setFilterById(idPro);
				Cursor cursor = downManager.query(query);
				String size = "0";
				String sizeTotal = "0";
				if (cursor.moveToNext()) {
					size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
					sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				}
				cursor.close();
				FileUtils.progressBar.setMax(Integer.valueOf(sizeTotal));
				FileUtils.progressBar.setProgress(Integer.valueOf(size));
				//ses.shutdown();
				if(Integer.valueOf(size)>=Integer.valueOf(sizeTotal)){
					//VideoDownLoadDialog.videoDownLoadDialog.dismiss();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

	}

	// private void queryTaskByIdandUpdateView(long id){
	// DownloadManager.Query query = new DownloadManager.Query();
	// query.setFilterById(id);
	// Cursor cursor= downManager.query(query);
	// String size="0";
	// String sizeTotal="0";
	// if(cursor.moveToNext()){
	// size=
	// cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
	// sizeTotal =
	// cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
	// }
	// cursor.close();
	// progressBar.setMax(Integer.valueOf(sizeTotal));
	// progressBar.setProgress(Integer.valueOf(size));
	//
	// }

}
