package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.module.farment.VideoDisplayFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

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

	private static FrameLayout frameLayout;
	private FragmentManager fragmentManager;

	private static LinearLayout ll_spareparts;
	
	private static LayoutParams frameLayoutParams;
	
	private ImageView iv_start;
	
	private static String fileName; 
 
//	public static Handler handler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			if (msg.obj.equals("view_gone")) {
//				ll_spareparts.setVisibility(View.GONE);
//				frameLayout.setLayoutParams(
//						new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
//				context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			}
//
//		}
//
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_video_display);

		init();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		super.init();
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
		fileName=getIntent().getStringExtra("fileName");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		frameLayout = (FrameLayout) findViewById(R.id.fl_video_display);
		ll_spareparts = (LinearLayout) findViewById(R.id.ll_spareparts);
		
		iv_start=(ImageView) findViewById(R.id.iv_start);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		frameLayoutParams=(LayoutParams) frameLayout.getLayoutParams();
		
		fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fl_video_display, new VideoDisplayFargmentActivity(fileName)).commit();
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
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当新设置中，屏幕布局模式为横排时
			ll_spareparts.setVisibility(View.GONE);
			frameLayout.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		}else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 当新设置中，屏幕布局模式为束排时
			ll_spareparts.setVisibility(View.VISIBLE);
			frameLayout.setLayoutParams(frameLayoutParams);		
		}
		super.onConfigurationChanged(newConfig);
	}
	
	
	private OnClickListener onClicListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.iv_start:
			//	VideoDownLoadDialog.showVideoDownLoadDialog(context,"videoTest.mp4");
				break;

			default:
				break;
			}
		}
	};

}
