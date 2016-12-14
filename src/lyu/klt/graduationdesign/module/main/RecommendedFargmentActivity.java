package lyu.klt.graduationdesign.module.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import lyu.klt.graduationdesign.module.adapter.FragmentViewPagerAdapter;
import lyu.klt.graduationdesign.util.FargmentTabUtil;
import lyu.klt.graduationdesign.view.PagerItem;
import lyu.klt.graduationdesign.view.SlidingTabLayout;

@SuppressLint("NewApi")
public class RecommendedFargmentActivity extends Fragment {
	private static final String TAG = RecommendedFargmentActivity.class.getSimpleName();
	private Activity context;
	public FargmentTabUtil fargmentTabUtil;

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
		View view = inflater.inflate(R.layout.fargment_recommended_layout2, null);
		this.view = view;
		this.init(view);
		return view;
	}
	//
	// @Override
	// public void onAttach(Context context) {
	// // TODO Auto-generated method stub
	// super.onAttach(context);
	// this.context = (Activity)context;
	// }

	public void init(View view) {
		this.initUtil();
		this.initData();
		this.initView(view);
		this.initViewData();
		this.initEvent();
		this.startGame();
	}

	public void initUtil() {
		// context = this.getActivity();
		// fargmentTabUtil=new FargmentTabUtil(context,view);
	}

	public void initData() {

	}

	public void initView(View view) {
		
		slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.id_tab) ;
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);

	}

	public void initViewData() {
		isPrepared = true;
		fragments.add(new HomeFragment());
		fragments.add(new HomeFragment());

		
		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(this.getFragmentManager(), viewPager,
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
