package lyu.klt.graduationdesign.moudle.client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.util.Log;



public class MyApplication extends Application {
	

	public final static String TAG = MyApplication.class
			.getSimpleName();
	private static MyApplication mInstance = null;

	private List<Activity> mActivityList = new LinkedList<Activity>();
	//购物车商品
	
	
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

	// 添加Activity到容器中
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
