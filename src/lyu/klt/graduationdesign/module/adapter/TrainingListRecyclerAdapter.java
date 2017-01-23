package lyu.klt.graduationdesign.module.adapter;

import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(推荐模块下的子模块（训练）模块模块下的listView中嵌套的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class TrainingListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
		implements OnItemClickListener, OnItemLongClickListener {
	private final static String TAG = "TrainingListRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<TrainingDataListPo> trainingDataListPo;
	
	
	private TrainingRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;

	public TrainingListRecyclerAdapter(Context context, int type,List<TrainingDataListPo> trainingDataListPo) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.trainingDataListPo = trainingDataListPo;
	}

	@Override
	public int getItemCount() {
		return trainingDataListPo.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_recommended, vg, false);
		holder = new TitleHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		TitleHolder holder = (TitleHolder) vh;
		LayoutParams params = holder.ll_item.getLayoutParams();
		if (mType == 1) { // 表示是线性布局
			// params.height = 50;
			holder.ll_item.setLayoutParams(params);
		} else if (mType == 2) { // 表示是网格布局
			// params.height=500;
			holder.ll_item.setLayoutParams(params);
		} 
//		else { // 表示是瀑布流网格布局
//			params.height = (int) Math.round(300 * Math.random());
//			if (params.height < 60) {
//				params.height = 60;
//			}
//			// 很奇怪，setLayoutParams对瀑布流网格不起作用，只能用setHeight
//			holder.tv_title.setHeight(params.height);
//		}
		TrainingDataListPo trainingDataPo = trainingDataListPo.get(position);
		
		holder.tv_list_title.setText(trainingDataPo.getTrianingList().get(0).getTitleName());
		mLayoutManager = new MyLinearLayoutManger(mContext, LinearLayout.HORIZONTAL, false);
		holder.rv_training_fargment.setLayoutManager(mLayoutManager);

		mAdapter = new TrainingRecyclerAdapter(mContext, 2, trainingDataPo.getTrianingList());
		holder.rv_training_fargment.setAdapter(mAdapter);
		holder.rv_training_fargment.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		holder.rv_training_fargment.addItemDecoration(decoration);
		
		
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				
				return true;
			}
		});
		
		
	}

	@Override
	public int getItemViewType(int position) {
		// 这里返回每项的类型，开发者可自定义头部类型与一般类型，
		// 然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class TitleHolder extends RecyclerView.ViewHolder {
		public View ll_item;
		//public TextView tv_title;
		public RecyclerView rv_training_fargment;
		public TextView tv_list_title;
	

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			rv_training_fargment = (RecyclerView) v.findViewById(R.id.rv_staggered);
			tv_list_title=(TextView) v.findViewById(R.id.tv_list_title);
		}

	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	private OnItemLongClickListener mOnItemLongClickListener;

	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.mOnItemLongClickListener = listener;
	}

	@Override
	public void onItemClick(View view, int position) {

		
	}

	@Override
	public void onItemLongClick(View view, int position) {
		
	}

	
}
