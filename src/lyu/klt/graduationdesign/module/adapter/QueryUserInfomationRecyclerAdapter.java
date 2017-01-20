package lyu.klt.graduationdesign.module.adapter;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.DietInfomation;
import lyu.klt.graduationdesign.moudle.activity.UserHomePageActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
/**
 * 
* @ClassName: DietReducedFatRecyclerAdapter 
* @Description: TODO(减脂模块recyclerView的Adapter) 
* @author 康良涛 
* @date 2016年12月16日 下午10:01:10 
*
 */
public class QueryUserInfomationRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>  {
		private final static String TAG = "QueryUserInfomationRecyclerAdapter";
		private Context mContext;
		private LayoutInflater mInflater;
		private int mType;
		private UserPo userPo;
		
		private String[] photoNameArray;
		private String photoName;
		private FileInputStream in = null;
		private Bitmap myBitmap;

		public QueryUserInfomationRecyclerAdapter(Context context, int type,UserPo userPo) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
			mType = type;
			this.userPo = userPo;
		}

		@Override
		public int getItemCount() {
			return 1;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
			View v = null;
			ViewHolder holder = null;
			v = mInflater.inflate(R.layout.recyclerview_list_item_user_info, vg, false);
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
		
			initUserHead(holder.user_picture);
			holder.tv_user_age.setText(userPo.getUserAge());
			holder.tv_user_name.setText(userPo.getUserName()+"("+userPo.getUserId()+")");
			holder.tv_user_sex.setText(userPo.getUserSex());
			
			// 列表项的点击事件需要自己实现
			holder.ll_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent=new Intent();
					intent.setClass(mContext, UserHomePageActivity.class);
					intent.putExtra("userId", userPo.getUserId());
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
			public ImageView user_picture;
			public TextView tv_user_name;
			public TextView tv_user_sex;
			public TextView tv_user_age;
			public TextView tv_user_address;
		

			public TitleHolder(View v) {
				super(v);
				ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
				user_picture= (ImageView) v.findViewById(R.id.user_picture);
				tv_user_name= (TextView) v.findViewById(R.id.tv_user_name);
				tv_user_sex= (TextView) v.findViewById(R.id.tv_user_sex);
				tv_user_age= (TextView) v.findViewById(R.id.tv_user_age);
				tv_user_address= (TextView) v.findViewById(R.id.tv_user_address);
				
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

		private void initUserHead(ImageView user_photo) {
			try {
				photoNameArray = userPo.getUserPhoto().split("/");
				photoName = photoNameArray[photoNameArray.length - 1];
				in = new FileInputStream(FileUtils.SDPATH + "image/" + photoName);
				myBitmap = BitmapFactory.decodeStream(in);
				user_photo.setImageBitmap(myBitmap);
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + photoName,
						user_photo, imageLoadingListener);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingStarted");
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingFailed");

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingComplete");
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingCancelled");
			}
		};

	}

