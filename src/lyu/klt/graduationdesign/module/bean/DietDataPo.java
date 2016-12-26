/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;

/** 
* @ClassName: DietDataPo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2016年12月24日 下午10:49:01 
*  
*/
public class DietDataPo implements Serializable{
	
	private String titleName;
	private String dinneTime;
	private String calories;
	private String dietName;
	private String productionTime;
	private String introduce;
	private String carbohydrate;
	private String protein;
	private String fat;
	private String dinneImage;
	private String dietType;
	private int dietId;
	
	
	public int getDietId() {
		return dietId;
	}
	public void setDietId(int dietId) {
		this.dietId = dietId;
	}
	public String getDietType() {
		return dietType;
	}
	public void setDietType(String dietType) {
		this.dietType = dietType;
	}
	public String getDinneImage() {
		return dinneImage;
	}
	public void setDinneImage(String dinneImage) {
		this.dinneImage = dinneImage;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getDinneTime() {
		return dinneTime;
	}
	public void setDinneTime(String dinneTime) {
		this.dinneTime = dinneTime;
	}
	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getDietName() {
		return dietName;
	}
	public void setDietName(String dietName) {
		this.dietName = dietName;
	}
	public String getProductionTime() {
		return productionTime;
	}
	public void setProductionTime(String productionTime) {
		this.productionTime = productionTime;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getCarbohydrate() {
		return carbohydrate;
	}
	public void setCarbohydrate(String carbohydrate) {
		this.carbohydrate = carbohydrate;
	}
	public String getProtein() {
		return protein;
	}
	public void setProtein(String protein) {
		this.protein = protein;
	}
	public String getFat() {
		return fat;
	}
	public void setFat(String fat) {
		this.fat = fat;
	}
	
	
	

}
