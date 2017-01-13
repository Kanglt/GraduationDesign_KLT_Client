/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/** 
* @ClassName: TrainingDataPo 
* @Description: TODO(训练数据) 
* @author 康良涛 
* @date 2016年12月23日 下午2:35:01 
*  
*/
public class TrainingDataPo implements Serializable{
	
	public String titleName;
	public String category;
	public int participation;
	public String trainingLevel;
	public String trainingTime;
	public String trainingVideo;
	public String trainingImage;
	public int trainingId;
	public String trainingCalories;
	public int trainingNum;
	
	
	public int getTrainingNum() {
		return trainingNum;
	}
	public void setTrainingNum(int trainingNum) {
		this.trainingNum = trainingNum;
	}
	public String getTrainingCalories() {
		return trainingCalories;
	}
	public void setTrainingCalories(String trainingCalories) {
		this.trainingCalories = trainingCalories;
	}
	public int getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	public String getTrainingImage() {
		return trainingImage;
	}
	public void setTrainingImage(String trainingImage) {
		this.trainingImage = trainingImage;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getParticipation() {
		return participation;
	}
	public void setParticipation(int participation) {
		this.participation = participation;
	}
	public String getTrainingLevel() {
		return trainingLevel;
	}
	public void setTrainingLevel(String trainingLevel) {
		this.trainingLevel = trainingLevel;
	}
	public String getTrainingTime() {
		return trainingTime;
	}
	public void setTrainingTime(String trainingTime) {
		this.trainingTime = trainingTime;
	}
	public String getTrainingVideo() {
		return trainingVideo;
	}
	public void setTrainingVideo(String trainingVideo) {
		this.trainingVideo = trainingVideo;
	}
	
	
	

}
