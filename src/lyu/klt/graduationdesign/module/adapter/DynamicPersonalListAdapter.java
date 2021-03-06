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
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lyu.klt.graduationdesign.moudle.activity.UserHomePageActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.module.bean.DynamicPPo;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;

/**
 * 
* @ClassName: DynamicPersonalListAdapter 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2017年1月22日 下午9:53:38 
*
 */
public class DynamicPersonalListAdapter extends BaseAdapter {

	private final static String TAG = "DynamicPersonalRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<DynamicPPo> dynamicPPoList;

	public DynamicPersonalListAdapter(Context context, List<DynamicPPo> dynamicPPoList) {
		this.mContext = context;
		this.dynamicPPoList = dynamicPPoList;

	}

	static class ViewHolder {
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
	}

	static class ViewHolderFooter {
		// ImageView status_img;
		// TextView status_message_detail_text;
		TextView tv_list_footer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dynamicPPoList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dynamicPPoList.get(position);
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
		DynamicPo dynamicPo = dynamicPPoList.get(position).getDynamicPo();
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_dynamic_friends, parent, false);
			
			holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.tv_dynamic_user_name = (TextView) convertView.findViewById(R.id.tv_dynamic_user_name);
			holder.tv_dynamic_time = (TextView) convertView.findViewById(R.id.tv_dynamic_time);
			holder.tv_dynamic_content = (TextView) convertView.findViewById(R.id.tv_dynamic_content);
			holder.tv_dynamic_forwarding_num = (TextView) convertView.findViewById(R.id.tv_dynamic_forwarding_num);
			holder.tv_dynamic_comments_num = (TextView) convertView.findViewById(R.id.tv_dynamic_comments_num);
			holder.tv_dynamic_thumb_up_num = (TextView) convertView.findViewById(R.id.tv_dynamic_thumb_up_num);
			holder.iv_dynamic_user_picture = (ImageView) convertView.findViewById(R.id.iv_dynamic_user_picture);
			holder.iv_dynamic_picture = (ImageView) convertView.findViewById(R.id.iv_dynamic_picture);
			
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
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
		
		return convertView;

	}
	
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingCancelled");
		}
	};

}
