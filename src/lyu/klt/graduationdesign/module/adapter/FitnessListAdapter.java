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
* @ClassName: FitnessListAdapter 
* @Description: TODO(训练模块下的listView的Adapter) 
* @author 康良涛 
* @date 2016年12月16日 下午1:54:38 
*  
*/
public class FitnessListAdapter extends BaseAdapter{

	

	private Context mContext;
	private List<HashMap<String, Object>> mList;
	
	


	public FitnessListAdapter(Context context,List<HashMap<String, Object>> mList
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_fitness, parent,false);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
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
