/**     
*/
package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.DietReducedFatRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.TrainingListRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.DietDataPAI;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: DietReducedFatFargmentActivity 
* @Description: TODO(饮食模块下的增肌数据模块) 
* @author 康良涛 
* @date 2016年12月25日 下午7:13:44 
*  
*/
public class DietAddMuscleFargmentActivity extends Fragment{

	private static final String TAG = DietAddMuscleFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	public SwipeRefreshLayout swipe_refresh_widget;
	private RecyclerView rv_add_muscle;
	private DietReducedFatRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	
	private String dinneTime;

	private List<DietDataPo> dietDataList;

	public DietAddMuscleFargmentActivity(String dinneTime){
		this.dinneTime=dinneTime;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_diet_reduced_fat_layout, null);
		this.init(view);
		return view;
	}

	@Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
		this.context = (Activity) context;
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
		// context = this.getActivity();
		
	}

	public void initData() {
		
	}

	public void initView(View view) {
	
		
		rv_add_muscle = (RecyclerView) view.findViewById(R.id.rv_reduced_fat);

	}

	public void initViewData() {
		isPrepared = true;


		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		// 设置布局管理器
		rv_add_muscle.setLayoutManager(layoutManager);
		// 设置为垂直布局，这也是默认的
		layoutManager.setOrientation(OrientationHelper.VERTICAL);
		// 设置Adapter
		rv_add_muscle.setAdapter(recycleAdapter);

		rv_add_muscle.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		rv_add_muscle.addItemDecoration(decoration);

	}

	public void initEvent() {

	}

	public void startGame() {
		
		DietDataPAI.getDietData_with_dinneTime_dietType(context, dinneTime, "增肌", dietData_with_dinneTime_dietTypeStringHttpResponseListener);
		
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			default:
				break;
			}
		}

	};



	
	private void initRVData() {
        mDatas = new ArrayList<String>();
        for ( int i=0; i < 1; i++) {
             mDatas.add( "item"+i);
       }
	}


	
	
	private AbStringHttpResponseListener dietData_with_dinneTime_dietTypeStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss(context, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("list"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					dietDataList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<DietDataPo>>() {
					}.getType());
					mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
					rv_add_muscle.setLayoutManager(mLayoutManager);
					mAdapter = new DietReducedFatRecyclerAdapter(context, 1, dietDataList);

					rv_add_muscle.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onStart");
			// 显示进度框
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			// HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};
}
