/**     
*/
package lyu.klt.graduationdesign.util;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/** 
* @ClassName: WindowsUtil 
* @Description: TODO(对屏幕进行操作) 
* @author 康良涛 
* @date 2016年11月30日 下午8:03:28 
*  
*/
public class WindowUtil {

	 /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public static int getNoHasVirtualKey(Activity activity) {
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }
    
    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    public static DisplayMetrics getHasVirtualKey(Activity activity,DisplayMetrics dm) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }
    
    
    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
        return screenWidth;
    }
    
    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getHeight();//屏幕宽度
        return screenWidth;
    }
}
