/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * @ClassName: HealthDetectionActivity
 * @Description: TODO(健康数据计算)
 * @author 康良涛
 * @date 2016年12月27日 下午9:15:15
 * 
 */
public class HealthDetectionActivity extends BaseActivity {
	private static final String TAG = HealthDetectionActivity.class.getSimpleName();
	private Activity context;

	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;
	
	
	private View fl_bmi, fl_body_fat_rate, fl_heart_rate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_health_detection_layout);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

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
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		fl_bmi = (FrameLayout) findViewById(R.id.fl_bmi);
		fl_body_fat_rate = (FrameLayout) findViewById(R.id.fl_body_fat_rate);
		fl_heart_rate = (FrameLayout) findViewById(R.id.fl_heart_rate);
		

		titlebar_view = findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return3));
		title_bar_text.setText("健康检测");

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();

		fl_bmi.setOnClickListener(onClickListener);
		fl_body_fat_rate.setOnClickListener(onClickListener);
		fl_heart_rate.setOnClickListener(onClickListener);
		title_bar_left_img.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.fl_bmi:
				intent.setClass(context, BMIActivity.class);
				startActivity(intent);
				break;
			case R.id.fl_body_fat_rate:
				intent.setClass(context, BodyFatRateActivity.class);
				startActivity(intent);
				break;
			case R.id.fl_heart_rate:
				intent.setClass(context, HeartRateDetectionActivity.class);
				startActivity(intent);
				break;
			case R.id.title_bar_left_img:
				finish();
				break;
			default:
				break;
			}
		}
	};
}
