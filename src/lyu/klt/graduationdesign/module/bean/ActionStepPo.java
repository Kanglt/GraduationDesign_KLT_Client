/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/** 
* @ClassName: ActionStepPo 
* @Description: TODO(训练动作步骤Po) 
* @author 康良涛 
* @date 2017年1月24日 下午3:01:15 
*  
*/
public class ActionStepPo implements Serializable{

	private int id;
	private int stepId;
	private String actionName;
	private String stepImage;
	private String stepText;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getStepImage() {
		return stepImage;
	}
	public void setStepImage(String stepImage) {
		this.stepImage = stepImage;
	}
	public String getStepText() {
		return stepText;
	}
	public void setStepText(String stepText) {
		this.stepText = stepText;
	}
	
	
}
