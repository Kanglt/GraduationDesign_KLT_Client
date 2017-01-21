/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/** 
* @ClassName: MusicSelectActivity 
* @Description: TODO(显示全部音乐类型) 
* @author 康良涛 
* @date 2016年12月26日 下午1:04:49 
*  
*/
public class MusicSelectActivity extends BaseActivity {

	
	private static final String TAG = MusicSelectActivity.class
			.getSimpleName();
	private Activity context;
	
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;
	
	
	private View rl_music_energy,rl_music_aerobic,rl_music_relax,rl_music_yoga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_seclect_music_layout);
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
		context=this;
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
		
		
		titlebar_view = findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);
		titlebar_right_text = (TextView) titlebar_right.findViewById(R.id.title_bar_right_text);
		
		
		rl_music_energy=(RelativeLayout)findViewById(R.id.rl_music_energy);
		rl_music_aerobic=(RelativeLayout)findViewById(R.id.rl_music_aerobic);
		rl_music_relax=(RelativeLayout)findViewById(R.id.rl_music_relax);
		rl_music_yoga=(RelativeLayout)findViewById(R.id.rl_music_yoga);
		
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return3));
		title_bar_text.setText("音乐");
//		title_bar_text.setTextColor(android.graphics.Color.parseColor("#FFFFFFFF"));
	//	titlebar_view.setBackgroundColor(android.graphics.Color.parseColor("#7c7c7c"));
	//	titlebar_right_text.setVisibility(View.VISIBLE);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		title_bar_left_img.setOnClickListener(onClickListener);
		rl_music_energy.setOnClickListener(onClickListener);
		rl_music_aerobic.setOnClickListener(onClickListener);
		rl_music_relax.setOnClickListener(onClickListener);
		rl_music_yoga.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}
	
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.rl_music_aerobic:
				intent.setClass(context, MusicListActivity.class);
				intent.putExtra("musicType", "aerobic");
				startActivity(intent);
				break;
			case R.id.rl_music_relax:
				intent.setClass(context, MusicListActivity.class);
				intent.putExtra("musicType", "Relax");
				startActivity(intent);
				break;
			case R.id.rl_music_energy:
				intent.setClass(context, MusicListActivity.class);
				intent.putExtra("musicType", "Energy");
				startActivity(intent);
				break;
			case R.id.rl_music_yoga:
				intent.setClass(context, MusicListActivity.class);
				intent.putExtra("musicType", "Yoga");
				startActivity(intent);
				break;
			default:
				break;
			}
			
		}
	};
	
}
