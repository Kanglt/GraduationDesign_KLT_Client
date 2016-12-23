/**     
*/
package lyu.klt.graduationdesign.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import lyu.klt.graduationdesign.view.HorizontalListView;

/** 
* @ClassName: ViewUtil 
* @Description: TODO(操作View相关的函数) 
* @author 康良涛 
* @date 2016年12月15日 下午6:55:45 
*  
*/
public class ViewUtil {

	/**
	 * 
	* @Title: inRangeOfView 
	* @author 康良涛 
	* @Description: TODO(判断屏幕触摸点是否在View中) 
	* @param @param view
	* @param @param ev
	* @param @return
	* @return boolean
	* @throws
	 */
	public static boolean inRangeOfView(View view, MotionEvent ev){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		if(ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())){
		return false;
		}
		return true;
		}

	
	/**
	 * 为了解决ListView在ScrollView中只能显示一行数据的问题
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	

}
