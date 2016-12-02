/**     
*/
package lyu.klt.graduationdesign.util;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

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
}
