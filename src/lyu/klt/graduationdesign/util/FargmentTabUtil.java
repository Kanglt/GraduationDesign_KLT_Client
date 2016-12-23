/**     
*/
package lyu.klt.graduationdesign.util;

import java.util.ArrayList;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.farment.DietFargmentActivity;
import lyu.klt.graduationdesign.module.farment.FitnessFargmentActivity;
import lyu.klt.graduationdesign.module.farment.RecommendedFargmentActivity;
import lyu.klt.graduationdesign.module.farment.TrainingFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/** 
* @ClassName: FargmentTabUtil 
* @Description: TODO(Tab导航栏相关操作) 
* @author 康良涛 
* @date 2016年11月29日 下午2:21:49 
*  
*/
public class FargmentTabUtil {
	
	/**
	 * 用于展示训练的Fragment
	 */
	private TrainingFargmentActivity trainingFargmentActivity;

	/**
	 * 用于展示饮食的Fragment
	 */
	private DietFargmentActivity dietFargmentActivity;


	/**
	 * 训练界面布局
	 */
	private View layout_training;

	/**
	 * 饮食界面布局
	 */
	private View layout_diet;

	
	/**
	 * 在Tab布局上显示训练标题的控件
	 */
	private TextView tv_training;

	/**
	 * 在Tab布局上显示饮食标题的控件
	 */
	private TextView tv_diet;
	
	private ImageView img_training_divider;
	private ImageView img_diet_divider;
	

	private View view;

	

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	
	private Activity context;
	
	public FargmentTabUtil(Activity context,View view){
		this.context=context;
//		LayoutInflater inflater = LayoutInflater.from(context);  
//		view = inflater.inflate(R.layout.fargment_recommended_layout, null);
		this.view=view;
		fragmentManager = context.getFragmentManager();
		init();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	public void init() {
		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();
	}


	public void initUtil() {

	}


	public void initData() {

	}


	public void initView() {
//		layout_training = view.findViewById(R.id.layout_training);
//		layout_diet = view.findViewById(R.id.layout_diet);
//		tv_training = (TextView) view.findViewById(R.id.tv_training);
//		tv_diet = (TextView) view.findViewById(R.id.tv_diet);
//		img_training_divider =(ImageView) view.findViewById(R.id.img_training_divider);
//		img_diet_divider = (ImageView) view.findViewById(R.id.img_diet_divider);
		
	}


	public void initViewData() {
//		ImageViewUtils.setImageViewWidth_div(context, messageImage,14);
//		ImageViewUtils.setImageViewWidth_div(context, contactsImage,14);
//		ImageViewUtils.setImageViewWidth_div(context, newsImage,14);
//		ImageViewUtils.setImageViewWidth_div(context, settingImage,14);
		
		
	}


	public void initEvent() {
		layout_training.setOnClickListener(onClickListener);
		layout_diet.setOnClickListener(onClickListener);
	}

	
	public void startGame() {
		
	}

	
	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	public void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			tv_training.setTextColor(Color.parseColor("#F2F2F2"));
			img_training_divider.setVisibility(View.VISIBLE);
			if (trainingFargmentActivity == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				trainingFargmentActivity = new TrainingFargmentActivity();
				//transaction.add(R.id.fargment_recommended, trainingFargmentActivity);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				//transaction.show(trainingFargmentActivity);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			
			tv_diet.setTextColor(Color.parseColor("#F2F2F2"));
			img_diet_divider.setVisibility(View.VISIBLE);
			if (dietFargmentActivity == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				dietFargmentActivity = new DietFargmentActivity();
				//transaction.add(R.id.fargment_recommended, dietFargmentActivity);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(dietFargmentActivity);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {	
		tv_training.setTextColor(Color.parseColor("#887c7c7c"));
		tv_diet.setTextColor(Color.parseColor("#887c7c7c"));
		img_diet_divider.setVisibility(View.GONE);
		img_training_divider.setVisibility(View.GONE);
//		newsImage.setImageResource(R.drawable.main_order_unpress);
//		newsText.setTextColor(Color.parseColor("#82858b"));
//		settingImage.setImageResource(R.drawable.main_news_unpress);
//		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (trainingFargmentActivity != null) {
			transaction.hide(trainingFargmentActivity);
		}
		if (dietFargmentActivity != null) {
			transaction.hide(dietFargmentActivity);
		}
		
	}
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
//			case R.id.layout_training:
//				// 当点击了消息tab时，选中第1个tab
//				setTabSelection(0);
//				break;
//			case R.id.layout_diet:
//				// 当点击了联系人tab时，选中第2个tab
//				setTabSelection(1);
//				break;
		
			default:
				break;
			}
		}
		
	};
}
