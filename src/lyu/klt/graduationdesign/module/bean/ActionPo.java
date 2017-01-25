/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/** 
* @ClassName: ActionPo 
* @Description: TODO(动作Po) 
* @author 康良涛 
* @date 2017年1月24日 下午3:17:26 
*  
*/
public class ActionPo implements Serializable{

	private int id;
	private String actionName;
	private String actionText;
	
	
	public String getActionText() {
		return actionText;
	}
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
}
