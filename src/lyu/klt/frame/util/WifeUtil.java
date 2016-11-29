package lyu.klt.frame.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifeUtil {
	public static String getewayIpS(Context context){
		WifiManager wm = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
		DhcpInfo di = wm.getDhcpInfo();
		long getewayIpL=di.gateway;
		String getwayIpS=long2ip(getewayIpL);//网关地址
		return getwayIpS;
	}
	
	static String long2ip(long ip){
		StringBuffer sb=new StringBuffer();
		sb.append(String.valueOf((int)(ip&0xff)));
		sb.append('.');
		sb.append(String.valueOf((int)((ip>>8)&0xff)));
		sb.append('.');
		sb.append(String.valueOf((int)((ip>>16)&0xff)));
		sb.append('.');
		sb.append(String.valueOf((int)((ip>>24)&0xff)));
		return sb.toString();
	}
	public static String getWay(Context context){
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wi = wm.getConnectionInfo();
		return wi.getBSSID();
	}
}
