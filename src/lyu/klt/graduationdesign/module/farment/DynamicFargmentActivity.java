package lyu.klt.graduationdesign.module.farment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import lyu.klt.graduationdesign.module.adapter.DynamicFragmentViewPagerAdapter;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.FargmentTabUtil;
import lyu.klt.graduationdesign.view.SlidingTabLayout;
/**
 * 
* @ClassName: DynamicFargmentActivity 
* @Description: TODO(动态模块Fargment) 
* @author 康良涛 
* @date 2016年12月15日 上午8:39:44 
*
 */
@SuppressLint("NewApi")
public class DynamicFargmentActivity extends Fragment  {
	private static final String TAG = DynamicFargmentActivity.class.getSimpleName();
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
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;//titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_dynamic_layout, null);
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
		
		titlebar_view = view.findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = view.findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) view.findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) view.findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout)titlebar_view.findViewById(R.id.title_bar_right_layout);
		
		slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.stl_dynamic) ;
		viewPager = (ViewPager) view.findViewById(R.id.vp_dynamic);

	}

	public void initViewData() {
		isPrepared = true;
		
//		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
//				R.drawable.btn_return));
		title_bar_text.setText("动态");
		
		MyApplication.recommended_vp=viewPager;
		
		fragments.add(new FriendsDynamicFargmentActivity());
		fragments.add(new HotDynamicFargmentActivity());

		
		DynamicFragmentViewPagerAdapter adapter = new DynamicFragmentViewPagerAdapter(this.getFragmentManager(), viewPager,
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
