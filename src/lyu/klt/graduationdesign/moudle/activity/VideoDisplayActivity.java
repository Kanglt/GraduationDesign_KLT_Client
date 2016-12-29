package lyu.klt.graduationdesign.moudle.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.DateUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.farment.VideoDisplayFargmentActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.MyCountDownTimer;

/**
 * 
 * @ClassName: VideoDisplayActivity
 * @Description: TODO(视频资料显示)
 * @author 康良涛
 * @date 2016年12月21日 下午8:17:04
 *
 */
public class VideoDisplayActivity extends BaseActivity {
	private static final String TAG = VideoDisplayActivity.class.getSimpleName();
	private static Activity context;

	private static TrainingDataPo trainingDataPo;
	private String[] fileNameStr;

	private static FrameLayout frameLayout;
	private FragmentManager fragmentManager;

	private static LinearLayout ll_spareparts;

	private static LayoutParams frameLayoutParams;

	public static ImageView iv_start;
	public static TextView tv_progress;

	private static String fileName;

	private InputStream in;
	public static Bitmap video_puaseBitmap;
	public static Bitmap video_startBitmap;
	private static int totalSize, totalSizeCountDownTimer;

	private static TextView tv_countdown;

	private TextView tv_category, tv_trainingTime, tv_trainingLevel;

	public static int theBottom = 3;

	private static SimpleDateFormat format;
	private static String date;

	public static boolean flag = false; // 用于判断视频是正在播放

	private static boolean islLandscape = false;// 是否横屏

	public static MyCountDownTimer myCountDownTimer;
	


	public static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.obj.equals("video_stanby")) {
				startTimer.start();
				totalSize = msg.arg1;
				totalSizeCountDownTimer = totalSize;
				myCountDownTimer = new MyCountDownTimer(context,totalSize, 1000,trainingDataPo);
				format = new SimpleDateFormat("mm:ss");
				date = format.format(new Date(totalSize));
				tv_countdown.setText(date);
				return;
			}
			if (msg.obj.equals("video_start")) {

				return;
			}

		}

	};

	public static Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_video_display);

		init();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// super.init();
		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();
	}

	@Override
	public void initUtil() {
		// TODO Auto-generated method stub
		super.initUtil();
		context = this;
		MyApplication.getInstance().addActivity(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();

		trainingDataPo = (TrainingDataPo) getIntent().getSerializableExtra("trainingDataPo");
		fileNameStr = trainingDataPo.getTrainingVideo().split("/");
		fileName = fileNameStr[fileNameStr.length - 1];

		in = context.getResources().openRawResource(R.drawable.btn_video_puase);
		video_puaseBitmap = BitmapFactory.decodeStream(in);
		in = context.getResources().openRawResource(R.drawable.btn_video_start);
		video_startBitmap = BitmapFactory.decodeStream(in);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		frameLayout = (FrameLayout) findViewById(R.id.fl_video_display);
		ll_spareparts = (LinearLayout) findViewById(R.id.ll_spareparts);

		iv_start = (ImageView) findViewById(R.id.iv_start);
		tv_progress = (TextView) findViewById(R.id.tv_progress);

		tv_countdown = (TextView) findViewById(R.id.tv_countdown);

		tv_category = (TextView) findViewById(R.id.tv_category);
		tv_trainingTime = (TextView) findViewById(R.id.tv_trainingTime);
		tv_trainingLevel = (TextView) findViewById(R.id.tv_trainingLevel);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		frameLayoutParams = (LayoutParams) frameLayout.getLayoutParams();

		fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fl_video_display, new VideoDisplayFargmentActivity(fileName))
				.commit();
		tv_category.setText(trainingDataPo.getTrainingCalories());
		tv_trainingTime.setText(trainingDataPo.getTrainingTime());
		tv_trainingLevel.setText(trainingDataPo.getTrainingLevel());
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		iv_start.setOnClickListener(onClicListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something...
			if (islLandscape) {
				islLandscape = false;
				ll_spareparts.setVisibility(View.VISIBLE);
				frameLayout.setLayoutParams(frameLayoutParams);
				Message msg = new Message();
				msg.obj = "click_tv_fullscreen";
				VideoDisplayFargmentActivity.mHandler2.sendMessage(msg);
				return true;
			}
			flag = true;
			theBottom = 3;
			myCountDownTimer.cancel();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当新设置中，屏幕布局模式为横排时
			islLandscape = true;
			ll_spareparts.setVisibility(View.GONE);
			frameLayout.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 当新设置中，屏幕布局模式为束排时
			islLandscape = false;
			ll_spareparts.setVisibility(View.VISIBLE);
			frameLayout.setLayoutParams(frameLayoutParams);
		}
		super.onConfigurationChanged(newConfig);
	}

	private OnClickListener onClicListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.iv_start:
				if (flag) {
					myCountDownTimer.pause();
					Message msg = new Message();
					msg.obj = "video_pause";
					VideoDisplayFargmentActivity.mHandler2.sendMessage(msg);
					flag = false;
					iv_start.setImageBitmap(video_startBitmap);
					VideoDisplayFargmentActivity.btn_videoPlay
							.setImageBitmap(VideoDisplayFargmentActivity.video_startBitmap);

					break;

				} else {
					myCountDownTimer.resume();
					Message msg = new Message();
					msg.obj = "video_start";
					VideoDisplayFargmentActivity.mHandler2.sendMessage(msg);
					iv_start.setImageBitmap(video_puaseBitmap);
					VideoDisplayFargmentActivity.btn_videoPlay
							.setImageBitmap(VideoDisplayFargmentActivity.video_puaseBitmap);
					flag = true;
					break;
				}

			default:
				break;
			}
		}
	};

	// /**
	// * 总时间倒计时
	// */
	// private static CountDownTimer TotalTimer = new CountDownTimer(totalSize,
	// 1000) {
	//
	// @Override
	// public void onTick(long millisUntilFinished) {
	// totalSizeCountDownTimer-=1000;
	// date = format.format(new Date(totalSizeCountDownTimer));
	// tv_progress.setText(date);
	//
	// }
	//
	// @Override
	// public void onFinish() {
	// date = format.format(new Date(totalSize));
	// tv_progress.setText(date);
	// }
	// };

	/**
	 * 开始倒计时
	 */
	private static CountDownTimer startTimer = new CountDownTimer(4000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {

			tv_progress.setText(theBottom + "");
			theBottom--;
		}

		@Override
		public void onFinish() {
			myCountDownTimer.start();
			Message msg2 = new Message();
			msg2.obj = "video_start";
			VideoDisplayFargmentActivity.mHandler2.sendMessage(msg2);
			iv_start.setImageBitmap(video_puaseBitmap);
			flag = true;
			tv_progress.setText(date + "");

		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		Message msg=new Message();
//		msg.obj="video_release";
//		VideoDisplayFargmentActivity.mHandler2.sendMessage(msg);
		myCountDownTimer.cancel();
		startTimer.cancel();
	}
	
	

}
