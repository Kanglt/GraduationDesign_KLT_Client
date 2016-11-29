package lyu.klt.graduationdesign.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNodeModel implements Comparable,Serializable {

	private String id;
	private String name;
	private String pId;
	private String nocheck;
	private String icon;
	private String iconcls;
	private String nodeType;
	private TreeNodeModel parentNode;
	private List<TreeNodeModel> childrenNodeList = new ArrayList<TreeNodeModel>(0);// 子节点;

	private Integer order = 99999;//排序(默认99999，没有赋值默认排最后)
	private String state = "closed";// 是否展开(open,closed)
	/**
	 * 树的层级
	 */
	private int LEVEL;
	
	public TreeNodeModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getNocheck() {
		return nocheck;
	}

	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public TreeNodeModel getParentNode() {
		return parentNode;
	}

	public void setParentNode(TreeNodeModel parentNode) {
		this.parentNode = parentNode;
	}

	public List<TreeNodeModel> getChildrenNodeList() {
		return childrenNodeList;
	}

	public void setChildrenNodeList(List<TreeNodeModel> childrenNodeList) {
		this.childrenNodeList = childrenNodeList;
	}
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 得到当前节点的层级
	 * 
	 * @Title: getLevel
	 * @return int
	 */
	public int getLEVEL() {
		return parentNode == null ? 0 : parentNode.getLEVEL() + 1;
	}

	public void setLEVEL(int lEVEL) {
		this.LEVEL = lEVEL;
	}

	@Override
	public int compareTo(Object o) {
		TreeNodeModel node = (TreeNodeModel)o;
		return this.order.compareTo(node.order);
	}
	/**
	 * 判断是否是根节点
	 * 
	 * @Title: isRoot
	 * @return boolean
	 */
	public boolean isRoot() {
		return parentNode == null;
	}

	/**
	 * 判断父节点是否处于展开状态
	 * 
	 * @Title: isParentExpend
	 * @return boolean
	 */
	public boolean isParentExpend() {
		if (parentNode == null)
			return false;
		if("closed".equals(parentNode.getState())){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 判断是否是叶子节点
	 * 
	 * @Title: isLeaf
	 * @return boolean
	 */
	public boolean isLeaf() {
		return childrenNodeList.size() == 0;
	}
}
