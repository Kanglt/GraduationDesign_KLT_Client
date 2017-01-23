/**     
*/
package lyu.klt.graduationdesign.module.bean;

/** 
* @ClassName: SystemVersionPo 
* @Description: TODO(版本信息Po) 
* @author 康良涛 
* @date 2017年1月23日 下午10:59:41 
*  
*/
public class SystemVersionPo {
	private int id;
	private String systemVersionId;
	private String systemApkURL;
	private String systemPlatform;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSystemVersionId() {
		return systemVersionId;
	}
	public void setSystemVersionId(String systemVersionId) {
		this.systemVersionId = systemVersionId;
	}
	public String getSystemApkURL() {
		return systemApkURL;
	}
	public void setSystemApkURL(String systemApkURL) {
		this.systemApkURL = systemApkURL;
	}
	public String getSystemPlatform() {
		return systemPlatform;
	}
	public void setSystemPlatform(String systemPlatform) {
		this.systemPlatform = systemPlatform;
	}
	
	
	
}
