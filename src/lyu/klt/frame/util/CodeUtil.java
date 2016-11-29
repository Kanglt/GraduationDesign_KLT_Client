package lyu.klt.frame.util;

import org.json.JSONException;
import org.json.JSONObject;

import lyu.klt.frame.util.CodeUtil.ErrorCode;

/**   
 * 服务端接口返回码  
 * @author   Dai
 * @version   1.0 
 * @datetime 2015年9月23日 下午4:49:40   
 */ 
public class CodeUtil {
	public enum ErrorCode {
		SUCCESS("操作成功", 2000), NOT_FOUND("查找不到请求的资源", 404), ALLOW_POST(
				"只允许Post访问", 1000), GET_FORBIDDEN("不允许执行get方法", 1001), POTST_FORBIDDEN(
				"不允许执行post方法", 1002), PARMS_EMPTY("参数不能为空", 1003),
				FLOW_REQUEST_SUCCESS("申请成功", 2000),
				FLOW_RECOMIT_SUCCESS("重新提交成功", 2000),
				FLOW_REJECT_SUCCESS("驳回成功", 2000),
				FLOW_AUDIT_SUCCESS("审核成功", 2000),
				FLOW_STOP_SUCCESS("撤销成功", 2000),
				FLOW_GET_SUCCESS("查询成功", 2000),
				LOGIN_SUCCESS("登录成功", 2000),
				GET_DATA_SUCCESS("签到成功", 2000),
		FAIL("操作失败", 2001), NO_DATA("没有数据", 2002), REGISTER_ERROR2(
				"该手机已达最大注册次数", 2003), REGISTER_ERROR3("该手机号码已被注册", 2004), REGISTER_ERROR4(
				"手机号码不能为空", 2005), REGISTER_ERROR5("手机码还未被注册过", 2006), REGISTER_ERROR6(
				"手机码已被注册过", 2007), LOGIN_FAIL_IMEI("该手机已自动创建过用户", 2008), LOGIN_FAIL_USER_PASSWORD(
				"用户名或密码错误", 2009), LOGIN_FAIL_USER_REQUIRED("用户名不能为空", 2010), LOGIN_FAIL_PWD_REQUIRED(
				"密码不能为空", 2011), LOGIN_FAIL_PWD_NEW_REQUIRED("新密码不能为空", 2012), LOGIN_FAIL_IMEI_REQUIRED(
				"imei不能为空", 2013), UPDATE_PWD_SUCCESS("修改密码成功", 2014), UPDATE_USER_NOT_EXISTS(
				"该手机号尚未注册", 2015), PHOTO_MODIFY_FAIL("修改头像失败", 2016), MODIFY_FAIL(
				"没有可以修改的资料", 2017), MODIFY_USERNAME("用户名已存在", 2018), USERNAME_NO_EXISTS(
				"用户名不存在", 2019), GET_VERSION("获取版本信息失败", 2020), GET_DATA_FAIL(
				"获取数据失败", 2021), COLLECTION_FAIL1("收藏失败", 2022), COLLECTION_FAIL2(
				"已经收藏过了", 2023), 
				FLOW_REQUEST_FAIL("流程提交失败", 4001),
				FLOW_RECOMIT_FAIL("流程重新提交失败", 4002),
				FLOW_AUDIT_FAIL("流程审核失败", 4003),
				FLOW_STOP_FAIL("流程撤销失败", 4004),
				FLOW_GET_FAIL("查询失败", 4005),
				FLOW_BANLI_FAIL("办理失败", 4006),
				DAKA_FAIL("打卡操作失败", 4007),;

		// 成员变量
		public String name;
		public int index;

		
		
		

		// 构造方法
		private ErrorCode(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 覆盖方法
		@Override
		public String toString() {
			JSONObject obj = new JSONObject();

			try {
				obj.put("code", this.index);
				obj.put("msg", this.name);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj.toString();
		}
	}
	/**   
	 * @Title: isCodeEquals   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param success
	 * @param: @param code
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 */
	public static boolean isCodeEquals(ErrorCode errorCode, String code) {
		
		if(StringUtil.isEmpty(code))
			return false;
		if((errorCode.index+"").equals(code))
			return true;
		return false;
	}

}

