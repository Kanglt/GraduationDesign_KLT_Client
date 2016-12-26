package lyu.klt.graduationdesign.moudle.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * 
 * @ClassName: LauncherActivity
 * @Description: TODO(欢迎页面)
 * @author 康良涛
 * @date 2016年12月18日 下午9:33:22
 *
 */
public class LauncherActivity extends Activity {

	private static final String TAG = LauncherActivity.class.getSimpleName();
	private Activity context;

	private Timer timer;
	private TimerTask task;

	// Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		initFiles();
		Log.e("Environment", Environment.getExternalStorageDirectory() + "/Focus/");
		// init();
		context = this;
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// MyApplication.getInstance().removeActivity(context);
				// LauncherActivity.this.finish();

				// AbSharedUtil.putString(context, Constant.ISLOGIN, "true");
				if (AbSharedUtil.getBoolean(context, Constant.ISLOGIN, false)) {
					Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
					startActivity(intent);
				}

			}

		};
		timer.schedule(task, 1000 * 2); // 2秒后
	}

	// @Override
	// public void init() {
	// // TODO Auto-generated method stub
	// super.init();
	// initUtil();
	// initData();
	// initView();
	// initViewData();
	// initEvent();
	// startGame();
	// }
	//
	// @Override
	// public void initUtil() {
	// // TODO Auto-generated method stub
	// super.initUtil();
	// context = this;
	// //MyApplication.getInstance().addActivity(this);
	//
	// // mHandler.sendEmptyMessageDelayed(0, 2000);
	// }
	//
	// @Override
	// public void initData() {
	// // TODO Auto-generated method stub
	// super.initData();
	//
	// timer = new Timer();
	// task = new TimerTask() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	//// MyApplication.getInstance().removeActivity(context);
	//// LauncherActivity.this.finish();
	// Intent intent = new Intent(context, MainActivityTest.class);
	// startActivity(intent);
	// }
	//
	// };
	// timer.schedule(task, 1000 * 2); // 2秒后
	//
	// // handler.postDelayed(new Runnable() {
	// // @Override
	// // public void run() {
	// // Intent intent = new Intent(context, MainActivityTest.class);
	// // startActivity(intent);
	// // context.finish();
	// // }
	// // }, 2000);
	// }
	//
	// @Override
	// public void initView() {
	// // TODO Auto-generated method stub
	// super.initView();
	// }
	//
	// @Override
	// public void initViewData() {
	// // TODO Auto-generated method stub
	// super.initViewData();
	// }
	//
	// @Override
	// public void initEvent() {
	// // TODO Auto-generated method stub
	// super.initEvent();
	// }
	//
	// @Override
	// public void startGame() {
	// // TODO Auto-generated method stub
	// super.startGame();
	// }

	public void initFiles() {
		try {
			if(!FileUtils.fileIsExists(FileUtils.SDPATH+"image")){
				FileUtils.createSDDir("image");
			}
			if(!FileUtils.fileIsExists(FileUtils.SDPATH+"localImgageLoader")){
				FileUtils.createSDDir("localImgageLoader");
			}
			if(!FileUtils.fileIsExists(FileUtils.SDPATH+"musics")){
				FileUtils.createSDDir("musics");
			}
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
