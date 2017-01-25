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
	public final static String FILE_SERVICE_DOWNLOAD_ACTIONIMAGE_URL = URL_BASE + "image/actionImage/";
	public final static String FILE_SERVICE_DOWNLOAD_VIDEO_URL = URL_BASE + "videos/";
	public final static String FILE_SERVICE_DOWNLOAD_MUSIC_URL = URL_BASE + "musics/";
	public final static String FILE_SERVICE_DOWNLOAD_APK_URL = URL_BASE + "androidApk/";
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
	public static String QUERYUSERPERSONALDYNAMIC_URL = URL_BASE
			+ "DataWebServiceMobile.service?queryUserPersonalDynamic";
	// 查询用户关注动态
	public static String QUERYUSERFOCUSDYNAMIC_URL = URL_BASE + "DataWebServiceMobile.service?queryUserFocusDynamic";
	// 查询用户关注动态
	public static String ADDUSERFOCUS_URL = URL_BASE + "DataWebServiceMobile.service?addUserFocus";
	// 查询用户主页信息
	public static String QUERYUSERHOMEPAGEINFO_URL = URL_BASE + "UserWebServiceMobile.service?queryUserHomePageInfo";
	// 查询用户关注列表
	public static String QUERYUSERFOCUSINFO_URL = URL_BASE + "UserWebServiceMobile.service?queryUserFocusInfo";
	// 查询用户粉丝列表
	public static String QUERYUSERFANSINFO_URL = URL_BASE + "UserWebServiceMobile.service?queryUserFansInfo";
	// 查询是否关注了对方
	public static String QUERYISFOCUS_URL = URL_BASE + "UserWebServiceMobile.service?queryIsFocus";
	// 取消对对方的关注
	public static String DELETEUSERFOCUS_URL = URL_BASE + "UserWebServiceMobile.service?deleteUserFocus";
	// 查询热门动态
	public static String QUERYHOTDYNAMIC_URL = URL_BASE + "DataWebServiceMobile.service?queryHotDynamic";
	// 查询热门动态
	public static String QUERYPERSONALINFO_URL = URL_BASE + "UserWebServiceMobile.service?queryPersonalInfo";
	// 查询热门动态
	public static String UPDATEUSERDYNAMICTHUMBUPNUM_URL = URL_BASE
			+ "UserWebServiceMobile.service?updateUserDynamicThumbUpNum";
	// 查询动态评论
	public static String QUERYDYNAMICCOMMENTS_URL = URL_BASE + "DataWebServiceMobile.service?queryDynamicComments";
	// 添加动态评论
	public static String ADDDYNAMICCOMMENTS_URL = URL_BASE + "DataWebServiceMobile.service?addDynamicComments";
	// 删除评论
	public static String DELETEDYNAMICCOMMENTS_URL = URL_BASE + "DataWebServiceMobile.service?deleteDynamicComments";
	// 查询系统版本信息
	public static String QUERYSYSTEMVERSIONINFOMATION_URL = URL_BASE
			+ "DataWebServiceMobile.service?querySystemVersionInfomation";
	// 查询动作列表
	public static String QUERYACTION_URL = URL_BASE + "DataWebServiceMobile.service?queryAction";
	// 查询动作步骤
	public static String QUERYACTIONSTEP_URL = URL_BASE + "DataWebServiceMobile.service?queryActionStep";
	// 查询用户饮食收藏
	public static String QUERYUSERDIET_URL = URL_BASE + "UserWebServiceMobile.service?queryUserDiet";
	// 添加用户饮食收藏
	public static String ADDUSERDIET_URL = URL_BASE + "UserWebServiceMobile.service?addUserDiet";
	// 取消用户饮食收藏
	public static String DELETEUSERDIET_URL = URL_BASE + "UserWebServiceMobile.service?deleteUserDiet";
	// 查询活动
	public static String QUERYACTIVITY_URL = URL_BASE + "DataWebServiceMobile.service?queryActivity";

}
