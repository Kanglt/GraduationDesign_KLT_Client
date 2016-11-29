package lyu.klt.frame.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbToastUtil;

public class SystemUtil {

	public static final String MS_RE_SERVICE = "com.fjzx.frame.service.MessageReceiverServices";
	public static final String EXTER_FILES = "/zx";
	
	private static Intent intentMessage=null;
	public static void startMsReceiverService(Context context) {
		if(intentMessage==null){
			intentMessage = new Intent(MS_RE_SERVICE);
			context.startService(intentMessage);
		}
	}
	
	public static void stopMsReceiverService(Context context) {
		if(intentMessage != null){
			context.stopService(intentMessage);
			intentMessage = null;
			
		}
	}

	/**隐藏输入法
	 * @param context
	 */
	public static void hideSoftInput(Context context) {
		try {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			Log.e("SystemUtil", "hideInputManager Catch error,skip it!", e);
		}
	}

	public static void showKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
		if (isOpen)
			return;
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void toggleSoftInput(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		// boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
		// if (isOpen)
		// return;
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		format.setTimeZone(gmt);
		String current = format.format(Calendar.getInstance().getTime());
		return current;
	}

	public static String getCurrentYear() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		format.setTimeZone(gmt);
		String current = format.format(Calendar.getInstance().getTime());
		return current;
	}

	public static String getDayTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		format.setTimeZone(gmt);
		String current = format.format(Calendar.getInstance().getTime());
		return current;
	}

	public static String getALLTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		format.setTimeZone(gmt);
		String current = format.format(Calendar.getInstance().getTime());
		return current;
	}

	public static Date getDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method action is to get the phone screen width.
	 * 
	 * @param mContext
	 * @return The absolute width of the display in pixels
	 */
	public static int getScreenWidth(Context mContext) {

		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * This method action is to get the phone screen height.
	 * 
	 * @param mContext
	 * @return The absolute width of the display in pixels
	 */
	public static int getScreenHeight(Context mContext) {

		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static boolean isWifiConnected(Context context) {
		WifiManager manager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return manager.isWifiEnabled();
	}

	/**
	 * This method action is to get an CirCle Bitmap from an bitmap
	 * 
	 * @param paramBitmap
	 * @return CirCle Bitmap
	 */
	public static Bitmap getCirCleBitmap(Bitmap paramBitmap) {
		Bitmap localBitmap;
		if (paramBitmap == null) {
			localBitmap = null;
			return localBitmap;
		}
		localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(),
				paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		Rect localRect = new Rect(0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight());
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawCircle(paramBitmap.getWidth() / 2,
				paramBitmap.getWidth() / 2, paramBitmap.getWidth() / 2,
				localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
		paramBitmap.recycle();
		return localBitmap;
	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	public static boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}


	public static String md5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
		byte[] resultByte = messageDigest.digest(str.getBytes());
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < resultByte.length; i++) {
			result.append(Integer.toHexString(0xFF & resultByte[i]));
		}
		return result.toString();
	}

	/**
	 * 
	 * <方法功能描述>
	 * 
	 * @param textView
	 * @param format
	 * @param errorMessage
	 * @param hasNull
	 * @param
	 * @return
	 */
	public static boolean VerifyFormat(Context context,TextView textView, String format,
			String errorMessage, boolean hasNull, String nullMessage) {

		if (!StringUtil.hasText(textView.getText().toString())) {
			if (hasNull)
				return true;
			AbToastUtil.showToast(context, nullMessage);
			textView.setFocusable(true);
			return false;
		}

		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(textView.getText().toString().trim());
		if (matcher.matches())
			return true;
		textView.setFocusable(true);
		AbToastUtil.showToast(context, errorMessage);
		return false;
	}

	/**
	 * 
	 * <方法功能描述>
	 * 
	 * @param edittext
	 * @param format
	 * @param errorMessage
	 * @param hasNull
	 * @param
	 * @return
	 */
	public static boolean VerifyFormat(Context context,EditText textView, String format,
			String errorMessage, boolean hasNull, String nullMessage) {

		if (!StringUtil.hasText(textView.getText().toString())) {
			if (hasNull)
				return true;
			AbToastUtil.showToast(context, nullMessage);
			textView.setFocusable(true);
			return false;
		}

		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(textView.getText().toString().trim());
		if (matcher.matches())
			return true;
		textView.setFocusable(true);
		AbToastUtil.showToast(context, errorMessage);
		return false;
	}

	public static boolean isTimeOut() {
		Date now = new Date();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time2 = "20141201080000";
		cal1.setTime(now);
		try {
			cal3.setTime(sdf.parse(time2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (cal1.before(cal3)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method action is to get External Storage Availale Size.
	 * 
	 * @return 单位(MB)
	 */
	public static long getExternalStorageAvailaleSize() {

		File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// return availableBlocks * blockSize;
		// (availableBlocks * blockSize)/1024 //KB 单位
		return (availableBlocks * blockSize) / (1024 * 1024); // MB单位
	}

	/**
	 * This method action is to get File Directory Availale Size.
	 * 
	 * @return 单位(MB)
	 */
	public static long getFileDirectoryAvailaleSize(String filePath) {
		StatFs stat = new StatFs(filePath);
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// return availableBlocks * blockSize;
		// (availableBlocks * blockSize)/1024 //KB 单位
		return (availableBlocks * blockSize) / (1024 * 1024); // MB单位
	}

	/**
	 * Check if external storage is built-in or removable.
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}

	/**
	 * Get the external app cache directory.
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir();
		}

		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	/**
	 * Check if OS version has built-in external cache dir method.
	 * 
	 * @return
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path
	 *            The path to check
	 * @return The space available in bytes
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	/**
	 * This method action is to get app data size.
	 * 
	 * @return 单位(MB)
	 */
	public static double getAppDataSize(Context mContext) {
		long size, size1, size2, size3, size4, size5, size6;
		size = FileUtils.getSize(mContext.getCacheDir());
		size1 = FileUtils.getSize(mContext.getFilesDir());
		size2 = FileUtils.getSize(mContext.getExternalCacheDir());
		size3 = FileUtils.getSize(mContext.getExternalFilesDir(null));
		size4 = FileUtils.getSize(new File("/data/data/"
				+ mContext.getPackageName() + "/shared_prefs"));
		size5 = FileUtils.getSize(new File("/data/data/"
				+ mContext.getPackageName() + "/databases"));
		size6 = FileUtils.getSize(new File(Environment
				.getExternalStorageDirectory().getPath() + SystemUtil.EXTER_FILES));
		long totalSize = size + size1 + size2 + size3 + size4 + size5 + size6;
		return totalSize / (1024.0 * 1024.0);
	}

	public static String getVersionName(Context context) {
		PackageManager manager;
		PackageInfo info = null;
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static String getPackageName(Context context) {
		PackageManager manager;
		PackageInfo info = null;
		manager = context.getPackageManager();
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.packageName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	// /**
	// * @param appModel
	// * @param context
	// * @return -1: no update, 0: force update, 1: can update
	// */
	// public static int checkVersion(AppModel appModel, Context context) {
	// if (appModel == null) {
	// return -1;
	// }
	//
	// float current = Float.parseFloat(SystemUtil.getVersionName(context));
	// float get = 1.0f;
	// float getbase = 1.0f;
	// try {
	// get = Float.parseFloat(appModel.getVersion());
	// getbase = Float.parseFloat(appModel.getBaseVersion());
	// } catch (NumberFormatException e) {
	// AbLogUtil.e(
	// "version",
	// "NumberFormatException---"
	// + "mUpdateBean.getVersion()  mUpdateBean.getBaseVersion() error");
	// return -1;
	// } catch (Exception e) {
	// AbLogUtil.e(
	// "version",
	// "Exception---"
	// + "mUpdateBean.getVersion()  mUpdateBean.getBaseVersion() error");
	// return -1;
	// }
	//
	// AbLogUtil.d("version", "checkVersion---current  " + current);
	// AbLogUtil.d("version", "checkVersion---" + appModel.toString());
	// if (current < getbase) {
	// return 0;// force update
	// } else if (current >= getbase && current < get) {
	// if (appModel.getForceUpdate().equals("1"))
	// return 0;// force update
	// else
	// return 1;// can update
	// } else {
	// AbLogUtil.d("version", "none");
	// return -1;
	// }
	// }

	public static String getImie(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
	}

	/**
	 * This method action is send email.
	 * 
	 * @param emails
	 *            Other people email that you want to send. such as {
	 *            "553769958@qq.com","test@mail.com","1019702156@qq.com"}
	 * @param dialogTitle
	 *            chooser app to send email dialog title.
	 * @param subject
	 *            titile
	 * @param body
	 */
	public static void sendEmail(Context mContext, String[] emails,
			String dialogTitle, String subject, String body) {
		String uriText = "mailto:";
		Uri uri = Uri.parse(uriText);
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(uri);
		sendIntent.putExtra(Intent.EXTRA_EMAIL, emails); // recipients
		sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		mContext.startActivity(Intent.createChooser(sendIntent, dialogTitle));
		/*
		 * Intent emailIntent = new Intent(Intent.ACTION_SEND); // The intent //
		 * does not have a URI, so declare the "text/plain" MIME type
		 * emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
		 * emailIntent.putExtra(Intent.EXTRA_EMAIL, email); // recipients
		 * emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
		 * emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
		 * mContext.startActivity( Intent.createChooser(emailIntent,
		 * dialogTitle));
		 */

	}

	public static void shareMsg(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain");
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

//	public static boolean isMeizuDevice() {
//		String brand = android.os.Build.BRAND.toLowerCase();
//		if (StringUtil.isEmpty(brand))
//			return false;
//		if (brand.contains("mx") || brand.contains("meizu")
//				|| brand.contains("fly_me")) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	public static final String ACTION_MEDIA_SCANNER_SCAN_DIR = "android.intent.action.MEDIA_SCANNER_SCAN_DIR";

	public static void scanDirAsync(Context ctx, String dir) {
		Intent scanIntent = new Intent(ACTION_MEDIA_SCANNER_SCAN_DIR);
		scanIntent.setData(Uri.fromFile(new File(dir)));
		ctx.sendBroadcast(scanIntent);
	}

	public static void scanFileAsync(Context ctx, String filePath) {
		Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		scanIntent.setData(Uri.fromFile(new File(filePath)));
		ctx.sendBroadcast(scanIntent);
	}

	

	public static String getDiskDir(Context context, String type) {
		boolean sdCardEnable = checkExternalStorageMountAndWriteable();
		if (sdCardEnable) {

			return Environment.getExternalStoragePublicDirectory(type)
					.toString();
		} else {
			return context.getCacheDir().getPath().toString();
		}

	}

	/**
	 * This method action is to check sdcard whether mount and writeable.
	 * 
	 * @return
	 */
	public static boolean checkExternalStorageMountAndWriteable() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we
			// need to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		return (mExternalStorageAvailable && mExternalStorageWriteable);
	}

	public static Typeface getCusTomFontFromAssert(Context context, String path) {
		try {
			AssetManager mgr = context.getAssets();// 得到AssetManager
			Typeface tf = Typeface.createFromAsset(mgr, path);// 根据路径得到Typeface
			return tf;
		} catch (Exception e) {
			AbLogUtil.e("getCusTomFontFromAssert", e.getMessage().toString());
		}

		return Typeface.DEFAULT;

	}

	public static Typeface getCusTomFontFromFile(Context context, String path) {
		try {
			File file = new File(path);
			if (!file.exists())
				return Typeface.DEFAULT;
			Typeface tf = Typeface.createFromFile(path);// 根据路径得到Typeface
			return tf;
		} catch (Exception e) {
			AbLogUtil.e("getCusTomFontFromAssert", e.getMessage().toString());
		}

		return Typeface.DEFAULT;

	}

	public static boolean matchText(String text, String regEx) {
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(text);
		boolean b = matcher.matches();
		return b;
	}

	public static void skipToNetWorkSetting(Context context) {
		try {
			Intent intent1 = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			context.startActivity(intent1);
		} catch (Exception e) {
			System.err.println(e.getMessage().toString());
			Intent intent1 = new Intent(Settings.ACTION_SETTINGS);
			context.startActivity(intent1);

		}

	}
	
	public static void callPhone(Context context,String phoneNum){
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_CALL);
		//需要拨打的号码
		intent.setData(Uri.parse("tel:"+phoneNum));
		context.startActivity(intent);
	}
	public static void sendSMS(Context context,String phoneNum){
		Intent intent = new Intent();

		//系统默认的action，用来打开默认的短信界面
		intent.setAction(Intent.ACTION_SENDTO);

		// 需要发短息的号码
		intent.setData(Uri.parse("smsto:"+phoneNum));
		context.startActivity(intent);
	}
	
	
}
