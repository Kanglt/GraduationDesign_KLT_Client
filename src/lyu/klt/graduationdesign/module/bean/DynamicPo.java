/**     
*/
package lyu.klt.graduationdesign.module.bean;

/**
 * @ClassName: DynamicPo
 * @Description: TODO(动态的Po)
 * @author 康良涛
 * @date 2017年1月14日 下午8:17:32
 * 
 */
public class DynamicPo {

	String userId;
	String dynamicDate;
	String dynamicText;
	String dynamicImage;
	String userName;
	String userPhoto;
	int id;
	int dynamicForwardingNum;
	int dynamicCommentsNum;
	int dynamicThumbUpNum;

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

	
}
