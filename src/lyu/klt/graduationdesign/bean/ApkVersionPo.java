package lyu.klt.graduationdesign.bean;

/**
 * 
 * apk版本信息
 * @author   张斌
 * @version   1.0 
 * @datetime 2016年7月18日 上午9:51:16
 */
public class ApkVersionPo  extends BasePo {

	private String description;   //版本描述
	private String fileId;   //文件编号
	private Double version;   //版本
	private Double minVersion;   //最低版本
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public Double getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(Double minVersion) {
		this.minVersion = minVersion;
	}
	


	
}
	
