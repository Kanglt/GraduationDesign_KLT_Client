/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/**
 * @ClassName: DynamicPo
 * @Description: TODO(动态的Po)
 * @author 康良涛
 * @date 2017年1月14日 下午8:17:32
 * 
 */
public class DynamicPo implements Serializable{

	private String userId;
	private String dynamicDate;
	private String dynamicText;
	private String dynamicImage;
	private String userName;
	private String userPhoto;
	private int id;
	private int dynamicForwardingNum;
	private int dynamicCommentsNum;
	private int dynamicThumbUpNum;
	private int isThumbUp;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDynamicDate() {
		return dynamicDate;
	}
	public void setDynamicDate(String dynamicDate) {
		this.dynamicDate = dynamicDate;
	}
	public String getDynamicText() {
		return dynamicText;
	}
	public void setDynamicText(String dynamicText) {
		this.dynamicText = dynamicText;
	}
	public String getDynamicImage() {
		return dynamicImage;
	}
	public void setDynamicImage(String dynamicImage) {
		this.dynamicImage = dynamicImage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDynamicForwardingNum() {
		return dynamicForwardingNum;
	}
	public void setDynamicForwardingNum(int dynamicForwardingNum) {
		this.dynamicForwardingNum = dynamicForwardingNum;
	}
	public int getDynamicCommentsNum() {
		return dynamicCommentsNum;
	}
	public void setDynamicCommentsNum(int dynamicCommentsNum) {
		this.dynamicCommentsNum = dynamicCommentsNum;
	}
	public int getDynamicThumbUpNum() {
		return dynamicThumbUpNum;
	}
	public void setDynamicThumbUpNum(int dynamicThumbUpNum) {
		this.dynamicThumbUpNum = dynamicThumbUpNum;
	}
	public int getIsThumbUp() {
		return isThumbUp;
	}
	public void setIsThumUp(int isThumbUp) {
		this.isThumbUp = isThumbUp;
	}

	
	
	
}
