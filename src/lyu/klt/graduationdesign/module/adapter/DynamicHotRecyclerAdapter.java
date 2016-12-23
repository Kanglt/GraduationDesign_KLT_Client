package lyu.klt.graduationdesign.module.adapter;



import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
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
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
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
	private final static String TAG = "RecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private String[] mTitleArray;
	
	public DynamicHotRecyclerAdapter(Context context, int type, String[] titleArray) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		mTitleArray = titleArray;
	}

	@Override
	public int getItemCount() {
		return mTitleArray.length;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_dynamic_hot, vg, false);
		holder = new TitleHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		TitleHolder holder = (TitleHolder) vh;
		LayoutParams params = holder.ll_item.getLayoutParams();
		if (mType == 1) {  //表示是线性布局
			//params.height = 50;
			holder.ll_item.setLayoutParams(params);
		} else if (mType == 2) {  //表示是网格布局
			//params.height=500;
			holder.ll_item.setLayoutParams(params);
		} else {  //表示是瀑布流网格布局
			params.height = (int) Math.round(300 * Math.random());
			if (params.height < 60) {
				params.height = 60;
			}
			//很奇怪，setLayoutParams对瀑布流网格不起作用，只能用setHeight
			holder.tv_title.setHeight(params.height);
		}
		
		//列表项的点击事件需要自己实现
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
		//这里返回每项的类型，开发者可自定义头部类型与一般类型，
		//然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
		return 0;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	public class TitleHolder extends RecyclerView.ViewHolder {
		public View ll_item;
		public TextView tv_seq;
		public TextView tv_title;

		public TitleHolder(View v) {
			super(v);
			ll_item=(LinearLayout)v.findViewById(R.id.ll_item);
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
		String desc = String.format("您点击了第%d项，内容是%s", position+1, mTitleArray[position]);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onItemLongClick(View view, int position) {
		String desc = String.format("您长按了第%d项，内容是%s", position+1, mTitleArray[position]);
		Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
	}

}
