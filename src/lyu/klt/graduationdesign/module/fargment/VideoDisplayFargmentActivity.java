package lyu.klt.graduationdesign.module.fargment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.LayoutInflater;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.view.SurfaceHolder.Callback;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.SeekBar;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.DateUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.AddTrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.DialogUtils;

/**
 * 
 * @ClassName: PersonalFargmentActivity
 * @Description: TODO(视频显示)
 * @author 康良涛
 * @date 2016年12月16日 下午8:08:10
 *
 */
public class VideoDisplayFargmentActivity extends Fragment {
	private static final String TAG = VideoDisplayFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，数据重新加载。
	private boolean isLoadedDate;

	public static ImageView btn_videoPlay; // 用于开始和暂停的按钮
	private SurfaceView mSurfaceView; // 绘图容器对象，用于把视频显示在屏幕上
	private String url; // 视频播放地址
	private static MediaPlayer mediaPlayer; // 播放器控件
	private int postSize; // 保存义播视频大小
	private static SeekBar seekbar; // 进度条控件
	private static boolean flag = true; // 用于判断视频是否在播放中
	private RelativeLayout rl_bottom, rl_top;
	private boolean display; // 用于是否显示其他按钮
	private Button btn_back; // 返回按钮
	private View pbView; // ProgressBar
	private upDateSeekBar update; // 更新进度条用
	private int totalSize;

	private InputStream in;
	public static Bitmap video_puaseBitmap;
	public static Bitmap video_startBitmap;
	public static Bitmap btn_fullscreenBitmap;
	public static Bitmap btn_exitfullscreenBitmap;

	private static ImageView tv_fullscreen;

	private static boolean islLandscape = false;// 是否横屏

	private static String fileName;

	private Message msg;



	/**
	 * 更新进度条
	 */
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (mediaPlayer == null) {
				flag = false;
			} else if (mediaPlayer.isPlaying()) {
				flag = true;
				int position = mediaPlayer.getCurrentPosition();
				int mMax = mediaPlayer.getDuration();
				int sMax = seekbar.getMax();
				seekbar.setProgress(position * sMax / mMax);
			} else {
				return;
			}
		};
	};

	public static Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.obj != null) {
				if (msg.obj.equals("video_start")) {
					if (mediaPlayer != null) {
						mediaPlayer.start();
					}
					return;
				}
				if (msg.obj.equals("video_pause")) {
					if (mediaPlayer != null) {
						mediaPlayer.pause();
					}
					return;
				}
				if (msg.obj.equals("click_tv_fullscreen")) {
					islLandscape = true;
					tv_fullscreen.performClick();
					return;
				}

			}
		}

	};

	public VideoDisplayFargmentActivity(String fileName) {
		this.fileName = fileName;
		msg = new Message();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fargment_video_disply_layout, null);
		init(view);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void init(View view) {
		this.initUtil();
		this.initData();
		this.initView(view);
		this.initViewData();
		this.initEvent();
		this.startGame();
	}

	public void initUtil() {
		context = this.getActivity();
		// ((MainActivity) this.getActivity()).registerMyTouchListener(this);
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 应用运行时，保持屏幕高亮，不锁屏

	}

	public void initData() {
		url = Environment.getExternalStorageDirectory() + "/focus/videos/" + fileName; // 视频播放地址

	}

	public void initView(View view) {
		mediaPlayer = new MediaPlayer(); // 创建一个播放器对象
		update = new upDateSeekBar(); // 创建更新进度条对象
		btn_back = (Button) view.findViewById(R.id.btn_back); // 返回按钮
		seekbar = (SeekBar) view.findViewById(R.id.seekbar); // 进度条
		btn_videoPlay = (ImageView) view.findViewById(R.id.btn_videoPlay);
		btn_videoPlay.setEnabled(false); // 刚进来，设置其不可点击
		mSurfaceView = (SurfaceView) view.findViewById(R.id.mSurfaceView);
		mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // 不缓冲
		mSurfaceView.getHolder().setKeepScreenOn(true); // 保持屏幕高亮
		mSurfaceView.getHolder().addCallback(new surFaceView()); // 设置监听事件
		rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
		rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
		pbView = view.findViewById(R.id.pb);
		tv_fullscreen = (ImageView) view.findViewById(R.id.tv_fullscreen);

	}

	public void initViewData() {

		in = context.getResources().openRawResource(R.drawable.btn_video_puase);
		video_puaseBitmap = BitmapFactory.decodeStream(in);
		in = context.getResources().openRawResource(R.drawable.btn_video_start);
		video_startBitmap = BitmapFactory.decodeStream(in);
		in = context.getResources().openRawResource(R.drawable.btn_fullscreen);
		btn_fullscreenBitmap = BitmapFactory.decodeStream(in);
		in = context.getResources().openRawResource(R.drawable.btn_exitfullscreen);
		btn_exitfullscreenBitmap = BitmapFactory.decodeStream(in);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rl_bottom.getBackground().setAlpha(50);
		rl_top.getBackground().setAlpha(50);

	}

	public void initEvent() {
		setListener();
		seekbar.setEnabled(false);
		tv_fullscreen.setOnClickListener(onClickListener);
		btn_videoPlay.setOnClickListener(onClickListener);
		mSurfaceView.setOnClickListener(onClickListener);
		btn_back.setOnClickListener(onClickListener);

	}

	public void startGame() {
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.tv_fullscreen:

				if (islLandscape) {
					context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
					tv_fullscreen.setImageBitmap(btn_fullscreenBitmap);
					islLandscape = false;

				} else {
					context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
					tv_fullscreen.setImageBitmap(btn_exitfullscreenBitmap);
					islLandscape = true;
				}

				break;
			case R.id.btn_videoPlay:
				if (mediaPlayer.isPlaying()) {
					btn_videoPlay.setImageBitmap(video_startBitmap);
					mediaPlayer.pause();
					postSize = mediaPlayer.getCurrentPosition();
					VideoDisplayActivity.myCountDownTimer.pause();
					VideoDisplayActivity.iv_start.setImageBitmap(VideoDisplayActivity.video_startBitmap);
				} else {
					if (flag == false) {
						flag = true;
						new Thread(update).start();
					}
					mediaPlayer.start();
					btn_videoPlay.setImageBitmap(video_puaseBitmap);
					VideoDisplayActivity.myCountDownTimer.resume();
					VideoDisplayActivity.iv_start.setImageBitmap(VideoDisplayActivity.video_puaseBitmap);

				}
				break;
			case R.id.mSurfaceView:
				/**
				 * 点击屏幕，切换控件的显示，显示则应藏，隐藏，则显示
				 */
				if (display) {
					btn_videoPlay.setVisibility(View.GONE);
					rl_bottom.setVisibility(View.GONE);
					rl_top.setVisibility(View.GONE);
					display = false;
				} else {
					rl_bottom.setVisibility(View.VISIBLE);
					rl_top.setVisibility(View.VISIBLE);
					btn_videoPlay.setVisibility(View.VISIBLE);
					mSurfaceView.setVisibility(View.VISIBLE);
					/**
					 * 设置播放为全屏
					 */
					ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
					lp.height = LayoutParams.FILL_PARENT;
					lp.width = LayoutParams.FILL_PARENT;
					mSurfaceView.setLayoutParams(lp);
					display = true;
				}
				break;
			case R.id.btn_back:
				if (islLandscape) {
					// 设置成竖屏
					context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					tv_fullscreen.setBackgroundResource(R.drawable.btn_exitfullscreen);
					islLandscape = false;
				} else {
					VideoDisplayActivity.flag = true;
					VideoDisplayActivity.theBottom = 3;
					context.finish();
				}

				/**
				 * 停止播放
				 */
				// if (mediaPlayer.isPlaying()) {
				// mediaPlayer.stop();
				// mediaPlayer.release();
				// }
				// mediaPlayer = null;

				break;
			default:
				break;
			}
		}

	};

	class PlayMovie extends Thread { // 播放视频的线程

		int post = 0;

		public PlayMovie(int post) {
			this.post = post;

		}

		@Override
		public void run() {
			Message message = Message.obtain();
			try {
				Log.i("hck", "runrun  " + url);
				mediaPlayer.reset(); // 回复播放器默认
				mediaPlayer.setDataSource(url); // 设置播放路径
				mediaPlayer.setDisplay(mSurfaceView.getHolder()); // 把视频显示在SurfaceView上
				mediaPlayer.setOnPreparedListener(new Ok(post)); // 设置监听事件
				mediaPlayer.prepare(); // 准备播放
			} catch (Exception e) {
				message.what = 2;
				Log.e("hck", e.toString());
			}

			super.run();
		}
	}

	class Ok implements OnPreparedListener {
		int postSize;

		public Ok(int postSize) {
			this.postSize = postSize;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			Log.i("hck", "play");
			Log.i("hck", "post " + postSize);
			pbView.setVisibility(View.GONE); // 准备完成后，隐藏控件
			btn_videoPlay.setVisibility(View.GONE);
			rl_bottom.setVisibility(View.GONE);
			rl_top.setVisibility(View.GONE);
			btn_videoPlay.setEnabled(true);
			display = false;
			if (mediaPlayer != null) {
				msg.obj = "video_stanby";
				totalSize = mediaPlayer.getDuration();
				msg.arg1 = totalSize;
				VideoDisplayActivity.handler.sendMessage(msg);
				// mediaPlayer.start(); // 开始播放视频
			} else {
				return;
			}
			if (postSize > 0) { // 说明中途停止过（activity调用过pase方法，不是用户点击停止按钮），跳到停止时候位置开始播放
				Log.i("hck", "seekTo ");
				mediaPlayer.seekTo(postSize); // 跳到postSize大小位置处进行播放
			}
			new Thread(update).start(); // 启动线程，更新进度条
		}
	}

	private class surFaceView implements Callback { // 上面绑定的监听的事件

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) { // 创建完成后调用
			if (postSize > 0 && url != null) { // 说明，停止过activity调用过pase方法，跳到停止位置播放
				new PlayMovie(postSize).start();
				flag = true;
				int sMax = seekbar.getMax();
				int mMax = mediaPlayer.getDuration();
				seekbar.setProgress(postSize * sMax / mMax);
				postSize = 0;
				pbView.setVisibility(View.GONE);
			} else {
				new PlayMovie(0).start(); // 表明是第一次开始播放
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) { // activity调用过pase方法，保存当前播放位置
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				postSize = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
				flag = false;
				pbView.setVisibility(View.VISIBLE);
			}
		}
	}

	private void setListener() {
		mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
			}
		});

		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // 视频播放完成
			@Override
			public void onCompletion(MediaPlayer mp) {
				flag = false;
				btn_videoPlay.setBackgroundResource(R.drawable.btn_video_start);
			}
		});

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {

			}
		});
		/**
		 * 如果视频在播放，则调用mediaPlayer.pause();，停止播放视频，反之，mediaPlayer.start()
		 * ，同时换按钮背景
		 */
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				int value = seekbar.getProgress() * mediaPlayer.getDuration() // 计算进度条需要前进的位置数据大小
						/ seekbar.getMax();
				mediaPlayer.seekTo(value);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

			}
		});
	}

	class upDateSeekBar implements Runnable {

		@Override
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
	}

	@Override
	public void onDestroy() { // activity销毁后，释放资源
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		System.gc();
	}



}
