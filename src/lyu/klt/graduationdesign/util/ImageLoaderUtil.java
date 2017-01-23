package lyu.klt.graduationdesign.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import lyu.klt.frame.util.BitmapUtil;
import lyu.klt.frame.util.SystemUtil;
import lyu.klt.graduationdesign.moudle.client.MyApplication;


public class ImageLoaderUtil {

	private ImageLoader imageLoader = null;
	private static DisplayImageOptions options = null;
	private final  String diskCacheDir = Environment
			.getExternalStorageDirectory().getPath()
			+ "/focus"
			+ "/imageLoader";
	private final  String localPictureDir = Environment
			.getExternalStorageDirectory().getPath()
			+ "/focus"
			+ "/localImageLoader";
	private final  String localUrl = "file://"
			+ localPictureDir + File.separator;
	// String imageUri = "http://site.com/image.png"; // from Web
	private final  String SD_HEADER = "file://"; // from SD card
	// String imageUri = "content://media/external/audio/albumart/13"; // from
	// content provider
	public static   String ASSETS_IMG_URL = "assets://image.png"; // from assets
	public static String RES_IMG_RUL = "drawable://"; // from drawables (only images, non-9patch)
	public static  String SDCARD_IMG_URL = "file://";

	private static ImageLoaderUtil  imageLoaderUtil; 
	
	public static ImageLoaderUtil getInstance(){
		if(imageLoaderUtil == null){
			imageLoaderUtil = new ImageLoaderUtil();
		}
		return imageLoaderUtil;
	}
	
	private ImageLoaderUtil() {
		super();
		initImageLoader();
	}

	private ImageLoaderConfiguration getImageLoaderConfiguration() {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				MyApplication.getInstance())
				// .memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(5)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				// .discCacheSize(50 * 1024 * 1024)
				.discCacheSize(
						(int) (SystemUtil.getExternalStorageAvailaleSize() / 4))
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(1000)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(new File(diskCacheDir)))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(MyApplication
								.getInstance(), 30 * 1000, 50 * 1000)) // connectTimeout
				// (5
				// s),
				// readTimeout
				// (30
				// s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		return config;

	}

	private void initImageLoader() {
		ImageLoaderConfiguration config = getImageLoaderConfiguration();
		// ImageLoader.getInstance().init(config);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		// imageLoader.init(ImageLoaderConfiguration.createDefault(MutilMediaApplication.getInstance()));

		initDisplayImageOptions();

	}

	private static  void initDisplayImageOptions() {
		if(options != null)
			return;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.image_loading) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_error)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_error) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				// .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.delayBeforeLoading(100)
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.decodingOptions(new BitmapFactory.Options()).build();// 构建完成

	}

	private  static DisplayImageOptions getDisplayImageOptions() {
		if(options == null)
			initDisplayImageOptions();
		return options;
	}

	public ImageLoader getImageLoader() {
		if (null == imageLoader)
			initImageLoader();
		return imageLoader;
	}

	public  DisplayImageOptions getDisplayImageOptions(
			BitmapFactory.Options options) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.image_loading) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_error)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_error) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.decodingOptions(options)// 设置图片的解码配置
				.delayBeforeLoading(100)
				// .delayBeforeLoading(3000)//int delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				//.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				// .decodingOptions(options)
				.build();// 构建完成
		return displayImageOptions;
	}

	private static void releaseObjects(HashMap<String, String> objectKeys) {
		if (objectKeys != null) {
			Bitmap bitmap;
			MemoryCache memoryCache = ImageLoaderUtil.getInstance().getImageLoader().getMemoryCache();
			Iterator iter = objectKeys.entrySet().iterator();
			String s = null;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// Object key = entry.getKey();
				Object val = entry.getValue();
				s = val.toString();
				s = objectKeys.get(s);
				bitmap = memoryCache.get(s);
				BitmapUtil.recycleBitmap(bitmap);
				memoryCache.remove(s);

			}

		}
	}

	private static void releaseObject(String objectKey) {
		if (objectKey != null) {
			Bitmap bitmap;
			MemoryCache memoryCache =ImageLoaderUtil.getInstance().getImageLoader().getMemoryCache();
			bitmap = memoryCache.get(objectKey);
			BitmapUtil.recycleBitmap(bitmap);
			memoryCache.remove(objectKey);
		}
	}
	
	public static void displayImage(String url,ImageView imageview,ImageLoadingListener imageLoadingListener){
		ImageAware imageAware = new ImageViewAware(imageview, false);
		if(imageLoadingListener == null){
			ImageLoaderUtil.getInstance().getImageLoader().displayImage(url, imageAware,ImageLoaderUtil.getDisplayImageOptions());
		}else{
			ImageLoaderUtil.getInstance().getImageLoader().displayImage(url, imageview,ImageLoaderUtil.getDisplayImageOptions(),imageLoadingListener);
		}
	}
	
	
	public static void displayImage(String url,ImageView imageview,DisplayImageOptions displayImageOptions,ImageLoadingListener imageLoadingListener){
		ImageAware imageAware = new ImageViewAware(imageview, false);
		if(imageLoadingListener != null){
			ImageLoaderUtil.getInstance().getImageLoader().displayImage(url, imageAware,displayImageOptions);
		}else{
			ImageLoaderUtil.getInstance().getImageLoader().displayImage(url, imageAware,displayImageOptions,imageLoadingListener);
		}
	}
	

	/**  
	 * @Title:  getDiskCacheDir <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getDiskCacheDir() {
		return diskCacheDir;
	}

	/**  
	 * @Title:  setImageLoader <BR>  
	 * @Description: please write your description <BR>  
	 * @return: ImageLoader <BR>  
	 */
	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}
	
}
