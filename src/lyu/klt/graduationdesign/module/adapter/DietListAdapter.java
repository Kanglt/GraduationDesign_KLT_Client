/**     
*/
package lyu.klt.graduationdesign.module.adapter;

import java.util.HashMap;
import java.util.List;
import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: DietListAdapter 
* @Description: TODO(推荐模块下的子模块（饮食）模块下的listView的Adapter) 
* @author 康良涛 
* @date 2016年12月16日 下午1:54:38 
*  
*/
public class DietListAdapter extends BaseAdapter{

	

	private Context mContext;
	private List<HashMap<String, Object>> mList;
	
	private RecyclerView rv_diet_fargment;
	private DietListRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private String[] yearArray = { "鼠年", "牛年", "虎年", "兔年", "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年" };


	public DietListAdapter(Context context,List<HashMap<String, Object>> mList
			) {
		this.mContext = context;
		this.mList=mList;
		
	}

	

	static class ViewHolder {
//		ImageView status_img;
//		TextView status_message_detail_text;
		TextView tv_list_title;
		RecyclerView rv_staggered;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		HashMap<String, Object> hm=mList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_recommended, parent,false);
			rv_diet_fargment = (RecyclerView) convertView.findViewById(R.id.rv_staggered);
			holder.tv_list_title=(TextView) convertView.findViewById(R.id.tv_list_title);
			holder.tv_list_title.setText(hm.get("list_text").toString());
			mLayoutManager = new MyLinearLayoutManger(mContext,LinearLayout.HORIZONTAL,false);
			rv_diet_fargment.setLayoutManager(mLayoutManager);
	
		//	mAdapter = new DietListRecyclerAdapter(mContext, 2, yearArray);
			rv_diet_fargment.setAdapter(mAdapter);
			rv_diet_fargment.setItemAnimator(new DefaultItemAnimator());
			// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
			SpacesItemDecoration decoration = new SpacesItemDecoration(5);
			rv_diet_fargment.addItemDecoration(decoration);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyApplication.rv_diet_fargment=rv_diet_fargment;
		return convertView;
//		View view = LayoutInflater.from(mContext).inflate(R.layout.listview_moudle_layout, null);
//		RecyclerView rv_staggered = (RecyclerView) view.findViewById(R.id.rv_staggered);
//		
//		mLayoutManager = new MyLinearLayoutManger(mContext);
//		rv_staggered.setLayoutManager(mLayoutManager);
//
//		mAdapter = new RecyclerAdapter(mContext, 3, yearArray);
//		rv_staggered.setAdapter(mAdapter);
//		rv_staggered.setItemAnimator(new DefaultItemAnimator());
//		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
//		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
//		rv_staggered.addItemDecoration(decoration);
//		
//		return view;
		
	}
}
