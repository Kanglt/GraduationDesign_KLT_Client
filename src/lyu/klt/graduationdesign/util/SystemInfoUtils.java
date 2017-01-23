/**     
*/
package lyu.klt.graduationdesign.util;

import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/** 
* @ClassName: SystemInfoUtils 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2017年1月24日 上午12:13:14 
*  
*/
public class SystemInfoUtils {

	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        String version = info.versionName;
	        return context.getString(R.string.version_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return context.getString(R.string.can_not_find_version_name);
	    }
	}
}
