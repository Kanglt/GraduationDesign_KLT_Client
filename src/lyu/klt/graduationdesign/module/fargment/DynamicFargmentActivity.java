package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lyu.klt.graduationdesign.module.adapter.DynamicFragmentViewPagerAdapter;
import lyu.klt.graduationdesign.moudle.activity.QueryUserActivity;
import lyu.klt.graduationdesign.moudle.activity.ReleaseDynamicActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
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
public class DynamicFargmentActivity extends Fragment {
	private static final String TAG = DynamicFargmentActivity.class.getSimpleName();
	private Activity context;

	private ViewPager viewPager;
	public List<Fragment> fragments = new ArrayList<Fragment>();
	public SlidingTabLayout slidingTabLayout;
	public ImageView img_dynamic_add;

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
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;

	private View relativeLayout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_dynamic_layout, null);
		this.view = view;
		this.init(view);
		return view;
	}

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
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);

		slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.stl_dynamic);
		viewPager = (ViewPager) view.findViewById(R.id.vp_dynamic);
		img_dynamic_add = (ImageView) view.findViewById(R.id.img_dynamic_add);
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

	}

	public void initViewData() {
		isPrepared = true;

		// title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
		// R.drawable.btn_return));
		title_bar_text.setText("动态");

		MyApplication.recommended_vp = viewPager;

		fragments.add(new FriendsDynamicFargmentActivity());
		fragments.add(new HotDynamicFargmentActivity());

		DynamicFragmentViewPagerAdapter adapter = new DynamicFragmentViewPagerAdapter(this.getFragmentManager(),
				viewPager, fragments);
		// adapter.setOnExtraPageChangeListener(new
		// FragmentViewPagerAdapter.OnExtraPageChangeListener(){
		// @Override
		// public void onExtraPageSelected(int i) {
		// System.out.println("Extra...i: " + i);
		// }
		// });

		slidingTabLayout.setCustomTabView(R.layout.recommended_fargment_tab_tv, 0);
		slidingTabLayout.setSelectedIndicatorColors(R.color.white);
		slidingTabLayout.setViewPager(adapter.getViewPager());

	}

	public void initEvent() {
		img_dynamic_add.setOnClickListener(onClickListener);
	}

	public void startGame() {
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.img_dynamic_add:
				showPopupWindow(v);
				break;

			default:
				break;
			}
		}

	};
	


	 private void showPopupWindow(View view) {

	        // 一个自定义的布局，作为显示的内容
	        View contentView = LayoutInflater.from(context).inflate(
	                R.layout.release_dynamic_add_layout, null);
	        final PopupWindow popupWindow = new PopupWindow(contentView,
	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
	        
	        TextView tv_release_dynamic=(TextView) contentView.findViewById(R.id.tv_release_dynamic);
	        TextView tv_add_friend=(TextView) contentView.findViewById(R.id.tv_add_friend);
	        
	        tv_release_dynamic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,ReleaseDynamicActivity.class);
					startActivity(intent);
					popupWindow.dismiss();
						
				}
			});
	        
	        tv_add_friend.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,QueryUserActivity.class);
					startActivity(intent);
					popupWindow.dismiss();
				}
			});
	        
	        
	      

	        popupWindow.setTouchable(true);

	        popupWindow.setTouchInterceptor(new OnTouchListener() {

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {

	                Log.i("mengdd", "onTouch : ");

	                return false;
	                // 这里如果返回true的话，touch事件将被拦截
	                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	            }
	        });

	        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	        // 我觉得这里是API的一个bug
//	        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//	                R.drawable.selectmenu_bg_downward));

	        // 设置好参数之后再show
	        popupWindow.showAsDropDown(view);

	    }
	
}
