package lyu.klt.graduationdesign.moudle.client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import lyu.klt.graduationdesign.module.ResideMenu.ResideMenu;



public class MyApplication extends Application {
	

	public final static String TAG = MyApplication.class
			.getSimpleName();
	private static MyApplication mInstance = null;

	private List<Activity> mActivityList = new LinkedList<Activity>();
	
	public static ResideMenu resideMenu;
	public static RecyclerView rv_training_fargment;
	public static RecyclerView rv_diet_fargment;
	public static ViewPager recommended_vp;
	public static ViewPager main_vp;
	public static List<ImageView> imageViewList;
	public static List<Bitmap> bitmapPress;
	public static List<Bitmap> bitmapUnPress;
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		//MessageServiceManger.startService(this);
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	// 从容器中删除Activity
	public void removeActivity(Activity activity) {
		mActivityList.remove(activity);
	}
	
	
	public void removeActivityByName(String name){
		try {
			for (Activity activity : mActivityList) {
				if (activity != null) {
					if(activity.getClass().getSimpleName().equals(name)){
						activity.finish();
						//break;
					}
					
				}
			}
		} catch (Exception e) {

			Log.d(TAG, e.toString());
		}
	}
	

	// 遍历所有Activity并finish
	public void exit() {
		try {
			for (Activity activity : mActivityList) {
				if (activity != null) {

					activity.finish();
				}
			}
		} catch (Exception e) {

			Log.d(TAG, e.toString());
		}
	}
	
	
	

	
}
