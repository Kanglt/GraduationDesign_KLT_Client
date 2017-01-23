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
import android.widget.Toast;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter.TitleHolder;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.DietInfomation;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
/**
 * 
* @ClassName: DietRecyclerAdapter 
* @Description: TODO(推荐模块下的子模块（饮食）模块下的listView中嵌套的RecyclerView的Adapter) 
* @author 康良涛 
* @date 2016年12月16日 下午10:01:10 
*
 */
public class DietRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>  {
		private final static String TAG = "DietRecyclerAdapter";
		private Context mContext;
		private LayoutInflater mInflater;
		private int mType;
		private List<DietDataPo> dietDataList;

		public DietRecyclerAdapter(Context context, int type, List<DietDataPo> dietDataList) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
			mType = type;
			this.dietDataList = dietDataList;
		}

		@Override
		public int getItemCount() {
			return dietDataList.size();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
			View v = null;
			ViewHolder holder = null;
			v = mInflater.inflate(R.layout.recyclerview_list_item_deit, vg, false);
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
		
			holder.tv_dietName.setText(dietDataList.get(position).getDietName());
			holder.tv_calories.setText(dietDataList.get(position).getCalories());
			
			String strArr[] = dietDataList.get(position).getDinneImage().split("/");
			String fileId=strArr[strArr.length-1];
			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_DIETIMAGE_URL + fileId, holder.iv_dinneImage,
					imageLoadingListener);
			
			// 列表项的点击事件需要自己实现
			holder.ll_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent=new Intent();
					intent.setClass(mContext, DietInfomation.class);
					intent.putExtra("dietDataPo", dietDataList.get(position));
					mContext.startActivity(intent);
					
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
			public TextView tv_dietName;
			public TextView tv_calories;
			public ImageView iv_dinneImage;
		

			public TitleHolder(View v) {
				super(v);
				ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
				
				tv_dietName=(TextView) v.findViewById(R.id.tv_dietName);
				tv_calories=(TextView) v.findViewById(R.id.tv_calories);
				iv_dinneImage=(ImageView) v.findViewById(R.id.iv_dinneImage);
				
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


		
		ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				Log.e("DietRecyclerAdapter", "onLoadingStarted");
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				Log.e("DietRecyclerAdapter", "onLoadingFailed");

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				Log.e("DietRecyclerAdapter", "onLoadingComplete");
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				Log.e("DietRecyclerAdapter", "onLoadingCancelled");
			}
		};

	}

