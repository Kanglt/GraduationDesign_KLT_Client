package lyu.klt.graduationdesign.moudle.client;

/** 
* @ClassName: UrlConstant 
* @Description: TODO(储存系统URL常数) 
* @author 康良涛 
* @date 2016年11月27日 上午10:22:48 
*  
*/
public class UrlConstant {

	private final static String URL_HEAD="http://";
	private final static String URL_IP="192.168.23.1";
	private final static String URL_PORT="8080";
	private final static String URL_PROJECTNAME="/GraduationDesign_KLT_Server/";
	private final static String URL_BASE=URL_HEAD+URL_IP+":"+URL_PORT+URL_PROJECTNAME;
	
	
	
	
	public static String TESTURL=URL_BASE+"TestMobile.service?getTestListForMobile";
	
	
}
