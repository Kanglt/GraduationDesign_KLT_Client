package lyu.klt.graduationdesign.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**   
 * 信息表辅助字段
 * @author   沈龙琴
 * @version   1.0 
 * @datetime 2016年7月12日 下午2:12:37   
 */ 
public class BasePo  implements Serializable {
	
	private String updateBy;
	private String updateByAddress;
	private Timestamp updateTime;
	private String createBy;
	private String createByAddress;
	private Timestamp createTime;
	private Integer recordVersion;

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateByAddress() {
		return updateByAddress;
	}

	public void setUpdateByAddress(String updateByAddress) {
		this.updateByAddress = updateByAddress;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateByAddress() {
		return createByAddress;
	}

	public void setCreateByAddress(String createByAddress) {
		this.createByAddress = createByAddress;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(Integer recordVersion) {
		this.recordVersion = recordVersion;
	}

}
