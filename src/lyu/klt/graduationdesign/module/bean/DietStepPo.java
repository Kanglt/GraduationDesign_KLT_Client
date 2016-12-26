/**     
*/
package lyu.klt.graduationdesign.module.bean;

/** 
* @ClassName: DietStepPo 
* @Description: TODO(食品制作步骤Po) 
* @author 康良涛 
* @date 2016年12月25日 下午1:03:27 
*  
*/
public class DietStepPo {
	
	private String stepDetailed;
	private String stepImage;
	private int stepnum;
	private String dietName;
	public String getStepDetailed() {
		return stepDetailed;
	}
	public void setStepDetailed(String stepDetailed) {
		this.stepDetailed = stepDetailed;
	}
	public String getStepImage() {
		return stepImage;
	}
	public void setStepImage(String stepImage) {
		this.stepImage = stepImage;
	}

	public int getStepnum() {
		return stepnum;
	}
	public void setStepnum(int stepnum) {
		this.stepnum = stepnum;
	}
	public String getDietName() {
		return dietName;
	}
	public void setDietName(String dietName) {
		this.dietName = dietName;
	}
	

}
