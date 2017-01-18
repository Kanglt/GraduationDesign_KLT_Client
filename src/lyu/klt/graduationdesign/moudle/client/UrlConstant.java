package lyu.klt.graduationdesign.moudle.client;

import android.net.Uri;

/**
 * @ClassName: UrlConstant
 * @Description: TODO(储存系统URL常数)
 * @author 康良涛
 * @date 2016年11月27日 上午10:22:48
 * 
 */
public class UrlConstant {

	private final static String URL_HEAD = "http://";
	private final static String URL_IP = "192.168.23.1";
	private final static String URL_PORT = "8080";
	private final static String URL_PROJECTNAME = "/GraduationDesign_KLT_Server/";
	private final static String URL_BASE = URL_HEAD + URL_IP + ":" + URL_PORT + URL_PROJECTNAME;

	public final static String FILE_SERVICE_DOWNLOAD_VIEWFILPPERIMAGE_URL = URL_BASE + "image/viewFilpper_img/";
	public final static String FILE_SERVICE_DOWNLOAD_USERPHOTO_URL = URL_BASE + "image/user_photo/";
	public final static String FILE_SERVICE_DOWNLOAD_DIETIMAGE_URL = URL_BASE + "image/dietImage/";
	public final static String FILE_SERVICE_DOWNLOAD_VIDEO_URL = URL_BASE + "videos/";
	public final static String FILE_SERVICE_DOWNLOAD_MUSIC_URL = URL_BASE + "musics/";
	public final static String FILE_SERVICE_DOWNLOAD_TRAININGIMAGE_URL = URL_BASE + "image/trainingImage/";
	public final static String FILE_SERVICE_DOWNLOAD_DYNAMICIMAGE_URL = URL_BASE + "image/user_dynamic_image/";
	
	public static Uri PHOTOURI;

	public static String TESTURL = URL_BASE + "TestMobile.service?getTestListForMobile";

	// 用户登入
	public static String USERLOGIN_URL = URL_BASE + "UserWebServiceMobile.service?login";
	// 获取用户信息
	public static String USERINFORMATION_URL = URL_BASE + "UserWebServiceMobile.service?getUserInformation";
	// 修改用户信息
	public static String UPDATEUSERINFORMATION_URL = URL_BASE + "UserWebServiceMobile.service?updateUserInformation";
	// 修改用户头像
	public static String UPDATEUSERPHOTO_URL = URL_BASE + "userPhotoUpdate";
	// 上传用户动态中的图片
	public static String ADDUSERDYNAMICIMAGE_URL = URL_BASE + "userDynamicImageUpload";
	// 获取推荐模块下训练模块的数据
	public static String TRAININGDATA_URL = URL_BASE + "DataWebServiceMobile.service?getTrainingData";
	// 获取饮食信息
	public static String DIETDATA_URL = URL_BASE + "DataWebServiceMobile.service?queryDietData";
	// 获取饮食信息
	public static String DIETDATA_DIETDATA_FOODMATERIA_URL = URL_BASE
			+ "DataWebServiceMobile.service?getDietData_foodMateria";
	// 获取饮食信息
	public static String DIETDATA_DIETDETAILE_STEP_URL = URL_BASE + "DataWebServiceMobile.service?getDietDetaile_step";
	// 获取对应就餐类型与就餐时间的视频数据
	public static String DIETDATA_WITH_DINNERTIME_DIETTYPE_URL = URL_BASE
			+ "DataWebServiceMobile.service?getDietData_with_dinneTime_dietType";
	// 获取对应音乐类型音乐数据
	public static String MUSICDATA_WITH_MUSICTYPE_URL = URL_BASE
			+ "DataWebServiceMobile.service?getMusicData_with_musicType";

	// 获取全部训练数据
	public static String TOTALTRAININGDATA_URL = URL_BASE + "DataWebServiceMobile.service?getTotalTraining";

	// 添加用户训练
	public static String ADDTRAININGDATA_URL = URL_BASE + "DataWebServiceMobile.service?addTraining";
	// 删除用户训练
	public static String DELETETRAININGDATA_URL = URL_BASE + "DataWebServiceMobile.service?deleteUserTraining";

	// 获取已添加训练
	public static String USERTRAININGDATA_URL = URL_BASE + "DataWebServiceMobile.service?queryUserTrainingData";
	// 获取已添加训练
	public static String USERTRAININGRECORD_URL = URL_BASE
			+ "DataWebServiceMobile.service?queryUserTrainingTotalRecord";
	// 添加训练记录
	public static String ADDUSERTRAININGRECORD_URL = URL_BASE + "DataWebServiceMobile.service?addUserTrainingRecord";
	// 推荐训练
	public static String RECOMMENDEDTRAINING_URL = URL_BASE + "DataWebServiceMobile.service?queryRecommendedTraining";

	// 获取用户身体数据
	public static String GETUSERBODYDATA_URL = URL_BASE + "DataWebServiceMobile.service?getUserBodyData";
	// 添加用户身体数据
	public static String ADDUSERBODYDATA_URL = URL_BASE + "DataWebServiceMobile.service?addUserBodyData";
	
	// 用户发布动态
	public static String ADDUSERDYNAMIC_URL = URL_BASE + "DataWebServiceMobile.service?addUserDynamic";
	// 查询用户个人动态
	public static String QUERYUSERPERSONALDYNAMIC_URL = URL_BASE + "DataWebServiceMobile.service?queryUserPersonalDynamic";
	// 查询用户关注动态
		public static String QUERYUSERFOCUSDYNAMIC_URL = URL_BASE + "DataWebServiceMobile.service?queryUserFocusDynamic";
	
}
