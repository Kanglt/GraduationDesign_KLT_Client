/**     
*/
package lyu.klt.graduationdesign.module.adapter;

import java.util.HashMap;
import java.util.List;
import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * @ClassName: FitnessListAdapter
 * @Description: TODO(训练模块下的listView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午1:54:38
 * 
 */
public class FitnessListAdapter extends BaseAdapter {
	private final static String TAG = "FitnessListAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<TrainingDataListPo> trainingDataPoList;

	public FitnessListAdapter(Context context, List<TrainingDataListPo> trainingDataPoList) {
		this.mContext = context;
		this.trainingDataPoList = trainingDataPoList;

	}

	static class ViewHolder {
		public View ll_item;
		// public TextView tv_title;
		public TextView tv_category;
		public TextView tv_levelAndTime;
		public TextView tv_num;
		public ImageView iv_trainingImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trainingDataPoList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trainingDataPoList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		TrainingDataListPo trainingDataListPo = trainingDataPoList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_fitness, parent, false);
			holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
			holder.tv_levelAndTime = (TextView) convertView.findViewById(R.id.tv_levelAndTime);
			holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			holder.iv_trainingImage = (ImageView) convertView.findViewById(R.id.iv_trainingImage);
			
			String strArr[] = trainingDataPoList.get(position).getTrianingList().get(0).getTrainingImage().split("/");
			String fileId=strArr[strArr.length-1];
			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_TRAININGIMAGE_URL + fileId, holder.iv_trainingImage,
					imageLoadingListener);
			
			
			holder.tv_category.setText(trainingDataPoList.get(position).getTrianingList().get(0).getCategory());
			holder.tv_num.setText(trainingDataPoList.get(position).getTrianingList().get(0).getParticipation()+"人收藏");
			holder.tv_levelAndTime.setText(trainingDataPoList.get(position).getTrianingList().get(0).getTrainingLevel()+" • "+trainingDataPoList.get(position).getTrianingList().get(0).getTrainingTime()+"分钟");
			// 列表项的点击事件需要自己实现
			holder.ll_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String[] fileName=trainingDataPoList.get(position).getTrianingList().get(0).getTrainingVideo().split("/");
					Intent intent=new Intent();
					if(!FileUtils.isFileExist("videos/"+fileName[fileName.length-1])){
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
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;

	}
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("UserTrainingRecyclerAdapter", "onLoadingCancelled");
		}
	};
	
}
