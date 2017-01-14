package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import lyu.klt.graduationdesign.module.adapter.RecommendedFragmentViewPagerAdapter;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.FargmentTabUtil;
import lyu.klt.graduationdesign.view.SlidingTabLayout;
/**
 * 
* @ClassName: RecommendedFargmentActivity 
* @Description: TODO(推荐模块Fargment) 
* @author 康良涛 
* @date 2016年12月15日 上午8:39:44 
*
 */
@SuppressLint("NewApi")
public class RecommendedFargmentActivity extends Fragment  {
	private static final String TAG = RecommendedFargmentActivity.class.getSimpleName();
	private Activity context;

	private ViewPager viewPager;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	public SlidingTabLayout slidingTabLayout;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	private View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_recommended_layout, null);
		this.view = view;
		this.init(view);
		return view;
	}
	
//	 @Override
//	 public void onAttach(Context context) {
//	 // TODO Auto-generated method stub
//	 super.onAttach(context);
//	 this.context = (Activity)context;
//	 }

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
		// fargmentTabUtil=new FargmentTabUtil(context,view);
	
	}

	public void initData() {

	}

	public void initView(View view) {
		
		slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.stl_recommended) ;
		viewPager = (ViewPager) view.findViewById(R.id.vp_recommended);

	}

	public void initViewData() {
		isPrepared = true;
		
		MyApplication.recommended_vp=viewPager;
		
		fragments.add(new TrainingFargmentActivity());
		fragments.add(new DietFargmentActivity());

		
		RecommendedFragmentViewPagerAdapter adapter = new RecommendedFragmentViewPagerAdapter(this.getFragmentManager(), viewPager,
				fragments);
		// adapter.setOnExtraPageChangeListener(new
				// FragmentViewPagerAdapter.OnExtraPageChangeListener(){
				// @Override
				// public void onExtraPageSelected(int i) {
				// System.out.println("Extra...i: " + i);
				// }
				// });

		slidingTabLayout.setCustomTabView(R.layout.recommended_fargment_tab_tv,0);
		slidingTabLayout.setSelectedIndicatorColors(R.color.white);
		slidingTabLayout.setViewPager(adapter.getViewPager());
	}

	public void initEvent() {

	}

	public void startGame() {
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}

	};





}
