package lyu.klt.graduationdesign.module.adapter;

import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.content.Intent;
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
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(推荐模块下的子模块（训练）模块模块下的listView中嵌套的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class TrainingRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
	private final static String TAG = "RecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<TrainingDataPo> trainingDataList;

	public TrainingRecyclerAdapter(Context context, int type, List<TrainingDataPo> trainingDataList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.trainingDataList = trainingDataList;
	}

	@Override
	public int getItemCount() {
		return trainingDataList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.recyclerview_list_item_training, vg, false);
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
	
		holder.tv_category.setText(trainingDataList.get(position).getCategory());
		holder.tv_participation.setText(trainingDataList.get(position).getParticipation()+"人收藏");
		holder.tv_trainingLevel.setText(trainingDataList.get(position).getTrainingLevel());
		holder.tv_trainingTime.setText(trainingDataList.get(position).getTrainingTime()+"分钟");
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] fileName=trainingDataList.get(position).getTrainingVideo().split("/");
				Intent intent=new Intent();
				if(!FileUtils.isFileExist("videos/"+fileName[fileName.length-1])){
					VideoDownLoadDialog.showVideoDownLoadDialog(mContext, fileName[fileName.length-1]);
				}else{
					intent.setClass(mContext, VideoDisplayActivity.class);
					intent.putExtra("fileName", fileName[fileName.length-1]);
					mContext.startActivity(intent);
				}
				AbToastUtil.showToast(mContext, trainingDataList.get(position).getCategory());
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AbToastUtil.showToast(mContext, trainingDataList.get(position).getCategory());
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
		public TextView tv_category;
		public TextView tv_participation;
		public TextView tv_trainingLevel;
		public TextView tv_trainingTime;
	

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_category=(TextView) v.findViewById(R.id.tv_category);
			tv_participation=(TextView) v.findViewById(R.id.tv_participation);
			tv_trainingLevel=(TextView) v.findViewById(R.id.tv_trainingLevel);
			tv_trainingTime=(TextView) v.findViewById(R.id.tv_trainingTime);
			
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


	
}
