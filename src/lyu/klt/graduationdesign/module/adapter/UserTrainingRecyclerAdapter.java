package lyu.klt.graduationdesign.module.adapter;

import java.util.List;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(用户已添加训练的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class UserTrainingRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
	private final static String TAG = "UserTrainingRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<TrainingDataListPo> trainingDataPoList;

	public UserTrainingRecyclerAdapter(Context context, int type, List<TrainingDataListPo> trainingDataPoList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.trainingDataPoList = trainingDataPoList;
	}

	@Override
	public int getItemCount() {
		return trainingDataPoList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.recycleview_item_fitness, vg, false);
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
	
		
		final String[] fileName=trainingDataPoList.get(position).getTrianingList().get(0).getTrainingVideo().split("/");
		final boolean isDownLoad=FileUtils.isFileExist("videos/"+fileName[fileName.length-1]);
		if(isDownLoad){
			holder.tv_isDownLoad.setText("已下载");
		}else{
			holder.tv_isDownLoad.setText("未下载");
		}
		
		holder.tv_finishNum.setText("完成"+trainingDataPoList.get(position).getTrianingList().get(trainingDataPoList.get(position).getTrianingList().size()-1).getTrainingNum()+"次");
		holder.tv_category.setText(trainingDataPoList.get(position).getTrianingList().get(0).getCategory());
		holder.tv_levelAndTime.setText(trainingDataPoList.get(position).getTrianingList().get(0).getTrainingTime()+"分钟");
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent();
				if(!isDownLoad){
					DownLoadDialog.showVideoDownLoadDialog(mContext, fileName[fileName.length-1]);
				}else{
					intent.setClass(mContext, VideoDisplayActivity.class);
					intent.putExtra("trainingDataPo", trainingDataPoList.get(position).getTrianingList().get(0));
					mContext.startActivity(intent);
				}
				//AbToastUtil.showToast(mContext, trainingDataPoList.get(position).getTrianingList().get(0).getCategory());
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
			//	AbToastUtil.showToast(mContext, trainingDataPoList.get(position).getTrianingList().get(0).getCategory());
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
		public TextView tv_levelAndTime;
		public TextView tv_isDownLoad;
		public TextView tv_finishNum;
	

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_category=(TextView) v.findViewById(R.id.tv_category);
			tv_levelAndTime=(TextView) v.findViewById(R.id.tv_levelAndTime);
			tv_isDownLoad=(TextView) v.findViewById(R.id.tv_isDownLoad);
			tv_finishNum=(TextView) v.findViewById(R.id.tv_finishNum);
			
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
