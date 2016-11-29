package lyu.klt.graduationdesign.bean;




/**
 * 
 * 账单信息   
 * @author   张斌
 * @version   1.0 
 * @datetime 2016年7月19日 下午5:11:01
 */
public class BillPo extends BasePo{


	private String  billTime; //事件	
	/**
	 *  1-充值 . 2-补贴 .3消费 .4统筹 5交易冲正
	 */
	private Integer billType;	
	private Double  billMoney;	//金额
	private String  billDescribe;//描述
	private Integer billNum;	 //数量
	private Integer _ROW_NUMBER;
	
	public String getBillTime() {
		return billTime;
	}
	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	public Double getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(Double billMoney) {
		this.billMoney = billMoney;
	}
	public String getBillDescribe() {
		return billDescribe;
	}
	public void setBillDescribe(String billDescribe) {
		this.billDescribe = billDescribe;
	}
	public Integer getBillNum() {
		return billNum;
	}
	public void setBillNum(Integer billNum) {
		this.billNum = billNum;
	}
	public Integer get_ROW_NUMBER() {
		return _ROW_NUMBER;
	}
	public void set_ROW_NUMBER(Integer _ROW_NUMBER) {
		this._ROW_NUMBER = _ROW_NUMBER;
	} 
	
	
	
	
	
	
}