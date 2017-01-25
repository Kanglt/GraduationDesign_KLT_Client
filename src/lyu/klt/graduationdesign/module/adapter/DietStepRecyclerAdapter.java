package lyu.klt.graduationdesign.module.adapter;

import java.util.List;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
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
import lyu.klt.graduationdesign.module.bean.DietStepPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(推荐模块下的子模块（训练）模块模块下的listView中嵌套的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class DietStepRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
		implements OnItemClickListener, OnItemLongClickListener {
	private final static String TAG = "StepRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<DietStepPo> dietStepPoListPo;

	private MyLinearLayoutManger mLayoutManager;

	public DietStepRecyclerAdapter(Context context, int type,List<DietStepPo> dietStepPoListPo) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.dietStepPoListPo = dietStepPoListPo;
	}

	@Override
	public int getItemCount() {
		return dietStepPoListPo.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_step, vg, false);
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

		DietStepPo dietStepPo = dietStepPoListPo.get(position);
		
		String strArr[] = dietStepPoListPo.get(position).getStepImage().split("/");
		String fileId=strArr[strArr.length-1];
		ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_DIETIMAGE_URL + fileId, holder.iv_stepImage,
				imageLoadingListener);
		
		
		holder.tv_stepnum.setText(dietStepPo.getStepnum()+"");
		holder.tv_stepDetailed.setText(dietStepPo.getStepDetailed());
		//holder.iv_stepImage
		
		
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
		public TextView tv_stepnum;
		public TextView tv_stepDetailed;
		public ImageView iv_stepImage;
		
	

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_stepnum=(TextView) v.findViewById(R.id.tv_stepnum);
			tv_stepDetailed=(TextView) v.findViewById(R.id.tv_stepDetailed);
			iv_stepImage=(ImageView) v.findViewById(R.id.iv_stepImage);
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
			Log.e("StepRecyclerAdapter", "onLoadingCancelled");
		}
	};

}
