/**     
*/
package lyu.klt.graduationdesign.util;

import android.content.Context;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import lyu.klt.frame.ab.view.weight.MyRadioGroup.LayoutParams;

/** 
* @ClassName: ImageViewUtils 
* @Description: TODO(ImageView相关操作) 
* @author 康良涛 
* @date 2016年11月29日 下午12:22:28 
*  
*/
public class ImageViewUtils {
	
	/**
	 * 
	* @Title: setImageViewWidth_div 
	* @author 康良涛 
	* @Description: TODO(设置Imageview的最大宽度与最大高度是屏幕大小的x倍) 
	* @param @param context
	* @param @param imageView
	* @param @param params
	* @return void
	* @throws
	 */
	public static void setImageViewWidth_div(Context context,ImageView imageView,int params){
		int screenWidth = getScreenWidth(context);
//		ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//		lp.width = screenWidth;
//		lp.height = LayoutParams.WRAP_CONTENT;
//		imageView.setLayoutParams(lp);

		imageView.setMaxWidth(screenWidth/params);
		imageView.setMaxHeight(screenWidth/params);
		
	}
	/**
	 * 
	* @Title: setImageViewWidth_mul 
	* @author 康良涛 
	* @Description: TODO(设置Imageview的最大宽度与最大高度是屏幕大小的x倍) 
	* @param @param context
	* @param @param imageView
	* @param @param params
	* @return void
	* @throws
	 */
	public static void setImageViewWidth_mul(Context context,ImageView imageView,int params){
		int screenWidth = getScreenWidth(context);
//		ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//		lp.width = screenWidth;
//		lp.height = LayoutParams.WRAP_CONTENT;
//		imageView.setLayoutParams(lp);

		imageView.setMaxWidth(screenWidth*params);
		imageView.setMaxHeight(screenWidth*params);
		
	}
	
	//获取屏幕的宽度  
    public static int getScreenWidth(Context context) {  
        WindowManager manager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        Display display = manager.getDefaultDisplay();  
        return display.getWidth();  
    }  
    //获取屏幕的高度  
    public static int getScreenHeight(Context context) {  
        WindowManager manager = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        Display display = manager.getDefaultDisplay();  
        return display.getHeight();  
    }

}
