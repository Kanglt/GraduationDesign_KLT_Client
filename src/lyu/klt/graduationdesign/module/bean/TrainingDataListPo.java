/**     
*/
package lyu.klt.graduationdesign.module.bean;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: TrainingDataListPo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2016年12月23日 下午3:16:27 
*  
*/
public class TrainingDataListPo implements Serializable{
	
	public List<TrainingDataPo> trianingList;


	public List<TrainingDataPo> getTrianingList() {
		return trianingList;
	}

	public void setTrianingList(List<TrainingDataPo> trianingList) {
		this.trianingList = trianingList;
	}


}
