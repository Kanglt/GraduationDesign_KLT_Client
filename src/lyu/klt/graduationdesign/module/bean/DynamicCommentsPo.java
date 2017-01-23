/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/** 
* @ClassName: DynamicCommentsPo 
* @Description: TODO(动态评论Po) 
* @author 康良涛 
* @date 2017年1月22日 下午8:12:00 
*  
*/
public class DynamicCommentsPo implements Serializable{

	private int id;
	private int dynamicId;
	private String commentsUserId;
	private String commentsText;
	private String replyId;
	private String replyName;
	private String commentsUserName;
	private String releaseUserId;
	private int dynamicForwardingNum;
	private int dynamicCommentsNum;
	private int dynamicThumbUpNum;
	
	
	
	public String getReleaseUserId() {
		return releaseUserId;
	}
	public void setReleaseUserId(String releaseUserId) {
		this.releaseUserId = releaseUserId;
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
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public String getCommentsUserName() {
		return commentsUserName;
	}
	public void setCommentsUserName(String commentsUserName) {
		this.commentsUserName = commentsUserName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(int dynamicId) {
		this.dynamicId = dynamicId;
	}
	public String getCommentsUserId() {
		return commentsUserId;
	}
	public void setCommentsUserId(String commentsUserId) {
		this.commentsUserId = commentsUserId;
	}
	public String getCommentsText() {
		return commentsText;
	}
	public void setCommentsText(String commentsText) {
		this.commentsText = commentsText;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	
	
	
}
