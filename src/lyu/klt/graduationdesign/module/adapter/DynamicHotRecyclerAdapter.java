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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import lyu.klt.graduationdesign.module.adapter.DynamicFriendsRecyclerAdapter.TitleHolder;
import lyu.klt.graduationdesign.module.bean.DynamicPPo;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.moudle.activity.UserHomePageActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
/**
 * 
* @ClassName: DynamicFriendsRecyclerAdapter 
* @Description: TODO(动态模块下的子模块（热门）模块下的RecyclerView的Adapter) 
* @author 康良涛 
* @date 2016年12月16日 下午10:01:10 
*
 */
public class DynamicHotRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> 
		implements OnItemClickListener, OnItemLongClickListener {
	private final static String TAG = "DynamicHotRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<DynamicPPo> dynamicPPoList;

	public DynamicHotRecyclerAdapter(Context context, int type, List<DynamicPPo> dynamicPPoList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.dynamicPPoList = dynamicPPoList;
	}

	@Override
	public int getItemCount() {
		return dynamicPPoList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_dynamic_friends, vg, false);
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
		} else { // 表示是瀑布流网格布局
			params.height = (int) Math.round(300 * Math.random());
			if (params.height < 60) {
				params.height = 60;
			}
			// 很奇怪，setLayoutParams对瀑布流网格不起作用，只能用setHeight
			holder.tv_title.setHeight(params.height);
		}

		holder.tv_dynamic_user_name.setText(dynamicPPoList.get(position).getDynamicPo().getUserName());
		holder.tv_dynamic_time.setText(dynamicPPoList.get(position).getDynamicPo().getDynamicDate());
		holder.tv_dynamic_content.setText(dynamicPPoList.get(position).getDynamicPo().getDynamicText());
		holder.tv_dynamic_forwarding_num.setText(dynamicPPoList.get(position).getDynamicPo().getDynamicForwardingNum() + "");
		holder.tv_dynamic_comments_num.setText(dynamicPPoList.get(position).getDynamicPo().getDynamicCommentsNum() + "");
		holder.tv_dynamic_thumb_up_num.setText(dynamicPPoList.get(position).getDynamicPo().getDynamicThumbUpNum() + "");

		if (!dynamicPPoList.get(position).getDynamicPo().getDynamicImage().equals("isEmpty")) {
			String strArr1[] = dynamicPPoList.get(position).getDynamicPo().getDynamicImage().split("/");
			String fileId = strArr1[strArr1.length - 1];
			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_DYNAMICIMAGE_URL + fileId,
					holder.iv_dynamic_picture, imageLoadingListener);
		}else{
			holder.iv_dynamic_picture.setVisibility(View.GONE);
		}

		String strArr2[] = dynamicPPoList.get(position).getDynamicPo().getUserPhoto().split("/");
		String fileId2 = strArr2[strArr2.length - 1];
		ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + fileId2,
				holder.iv_dynamic_user_picture, imageLoadingListener);

		holder.iv_dynamic_user_picture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mContext,UserHomePageActivity.class);
				intent.putExtra("userId", dynamicPPoList.get(position).getDynamicPo().getUserId());
				mContext.startActivity(intent);
			}
		});
		
		
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(v, position);
				}
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mOnItemLongClickListener != null) {
					mOnItemLongClickListener.onItemLongClick(v, position);
				}
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
		public TextView tv_title;
		public ImageView iv_dynamic_user_picture;
		public TextView tv_dynamic_user_name;
		public TextView tv_dynamic_time;
		public TextView tv_dynamic_content;
		public ImageView iv_dynamic_picture;
		public TextView tv_dynamic_forwarding_num;
		public TextView tv_dynamic_comments_num;
		public TextView tv_dynamic_thumb_up_num;
		
		public RelativeLayout rl_dynamic_forwarding;
		public RelativeLayout rl_dynamic_comments;
		public RelativeLayout rl_dynamic_thumb_up;

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_dynamic_user_name = (TextView) v.findViewById(R.id.tv_dynamic_user_name);
			tv_dynamic_time = (TextView) v.findViewById(R.id.tv_dynamic_time);
			tv_dynamic_content = (TextView) v.findViewById(R.id.tv_dynamic_content);
			tv_dynamic_forwarding_num = (TextView) v.findViewById(R.id.tv_dynamic_forwarding_num);
			tv_dynamic_comments_num = (TextView) v.findViewById(R.id.tv_dynamic_comments_num);
			tv_dynamic_thumb_up_num = (TextView) v.findViewById(R.id.tv_dynamic_thumb_up_num);
			iv_dynamic_user_picture = (ImageView) v.findViewById(R.id.iv_dynamic_user_picture);
			iv_dynamic_picture = (ImageView) v.findViewById(R.id.iv_dynamic_picture);
			

			rl_dynamic_forwarding=(RelativeLayout) v.findViewById(R.id.rl_dynamic_forwarding);
			rl_dynamic_comments=(RelativeLayout) v.findViewById(R.id.rl_dynamic_comments);
			rl_dynamic_thumb_up=(RelativeLayout) v.findViewById(R.id.rl_dynamic_thumb_up);
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
			Log.e("DynamicHotRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicHotRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicHotRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DynamicHotRecyclerAdapter", "onLoadingCancelled");
		}
	};

}
