/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DietDinneTimeFragmentViewPagerAdapter;
import lyu.klt.graduationdesign.module.adapter.RecommendedFragmentViewPagerAdapter;
import lyu.klt.graduationdesign.module.farment.DietAddMuscleFargmentActivity;
import lyu.klt.graduationdesign.module.farment.DietFargmentActivity;
import lyu.klt.graduationdesign.module.farment.DietReducedFatFargmentActivity;
import lyu.klt.graduationdesign.module.farment.TrainingFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.SlidingTabLayout;

/** 
* @ClassName: DinneTimeActivity 
* @Description: TODO(根据就餐时间显示相应数据的界面) 
* @author 康良涛 
* @date 2016年12月25日 下午6:56:23 
*  
*/
public class DinneTimeActivity extends BaseActivity {

	
	private static final String TAG = DinneTimeActivity.class
			.getSimpleName();
	private Activity context;
	
	private ViewPager viewPager;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	public SlidingTabLayout slidingTabLayout;
	
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;
	
	private String dinneTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_dinnetime_layout);
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
		dinneTime=getIntent().getStringExtra("dinneTime");
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
		
		
		slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_dinneTime) ;
		viewPager = (ViewPager) findViewById(R.id.vp_dinneTime);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		fragments.add(new DietReducedFatFargmentActivity(dinneTime));
		fragments.add(new DietAddMuscleFargmentActivity(dinneTime));

		
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText(dinneTime);
		//titlebar_right_text.setVisibility(View.VISIBLE);
		
		DietDinneTimeFragmentViewPagerAdapter adapter = new DietDinneTimeFragmentViewPagerAdapter(this.getFragmentManager(), viewPager,
				fragments);
		
		slidingTabLayout.setCustomTabView(R.layout.recommended_fargment_tab_tv,0);
		slidingTabLayout.setSelectedIndicatorColors(R.color.white);
		slidingTabLayout.setViewPager(adapter.getViewPager());
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img.setOnClickListener(onClickListener);
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
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;

			default:
				break;
			}
		}
	};
}
