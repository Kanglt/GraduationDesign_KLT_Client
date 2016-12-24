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
	
	
	
	public final static String FILE_SERVICE_DOWNLOAD_VIEWFILPPERIMAGE_URL=URL_BASE+"image/viewFilpper_img/";
	public final static String FILE_SERVICE_DOWNLOAD_USERPHOTO_URL=URL_BASE+"image/user_photo/";
	public final static String FILE_SERVICE_DOWNLOAD_VIDEO_URL=URL_BASE+"videos/";
	
	
	public static String TESTURL=URL_BASE+"TestMobile.service?getTestListForMobile";
	
	//用户登入
	public static String USERLOGIN_URL=URL_BASE+"UserWebServiceMobile.service?login";
	//获取用户信息
	public static String USERINFORMATION_URL=URL_BASE+"UserWebServiceMobile.service?getUserInformation";
	//修改用户信息
	public static String UPDATEUSERINFORMATION_URL=URL_BASE+"UserWebServiceMobile.service?updateUserInformation";
	//修改用户头像
	public static String UPDATEUSERPHOTO_URL=URL_BASE+"userPhotoUpdate";
	//获取推荐模块下训练模块的数据
	public static String TRAININGDATA_URL=URL_BASE+"DataWebServiceMobile.service?getTrainingData";
	//获取饮食信息
	public static String DIETDATA_URL=URL_BASE+"DataWebServiceMobile.service?queryDietData";	
}
