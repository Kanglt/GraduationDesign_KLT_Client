/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
* @ClassName: UserPo 
* @Description: TODO(userPo) 
* @author 康良涛 
* @date 2016年11月28日 下午1:32:20 
*  
*/
public class UserPo implements Serializable{
	private String userId;
	private String userPassword;
	private String userName;
	private String userBirthday;
	private String userPhoneNumble;
	private String userSex;
	private String userEmail;
	private String userPhoto;
	private String userAge;
	private int focusNum;
	private int fansNum;
	private int dynamicNum;
	private int isFocus;
	
	
	
	
	

	public int getIsFocus() {
		return isFocus;
	}
	public void setIsFocus(int isFocus) {
		this.isFocus = isFocus;
	}
	public int getDynamicNum() {
		return dynamicNum;
	}
	public void setDynamicNum(int dynamicNum) {
		this.dynamicNum = dynamicNum;
	}
	public int getFocusNum() {
		return focusNum;
	}
	public void setFocusNum(int focusNum) {
		this.focusNum = focusNum;
	}
	public int getFansNum() {
		return fansNum;
	}
	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserPhoneNumble() {
		return userPhoneNumble;
	}
	public void setUserPhoneNumble(String userPhoneNumble) {
		this.userPhoneNumble = userPhoneNumble;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
