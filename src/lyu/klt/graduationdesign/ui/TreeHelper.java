/**
 * @Title: TreeHelper.java
 * @Package com.sloop.treeview.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年2月21日 上午3:19:27
 * @version V1.0
 */

package lyu.klt.graduationdesign.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.graduationdesign.bean.TreeNodeModel;

import com.lyu.graduationdesign_klt.R;

/**
 * 树形结构的帮助类 将元数据转换为节点
 * @ClassName: TreeHelper
 * @Description: 
 * @author sloop
 * @date 2015年2月21日 上午3:19:27
 *
 */

public class TreeHelper {

	/**
	 * 将用户数据转化为树形数据
	 * @Title: convertDatas2Nodes
	 * @param datas
	 * @return List<Node>	Node数据集
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<TreeNodeModel> convertDatas2Nodes(List<T> datas) throws IllegalAccessException, IllegalArgumentException {
		
		List<TreeNodeModel> nodes = new ArrayList<TreeNodeModel>();
		TreeNodeModel node = null;
		nodes = (List<TreeNodeModel>) datas;
		for (TreeNodeModel n : nodes) {
			setNodeIcon(n);
		}
		
		return nodes;
	}

	/**
	 * 获取排序后的节点数据
	 * @Title: getSortedNodes
	 * @param datas
	 * @return List<Node>
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<TreeNodeModel> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		
		List<TreeNodeModel> result = new ArrayList<TreeNodeModel>();		//排序完成的节点
		List<TreeNodeModel> nodes = convertDatas2Nodes(datas);	//转化后的所有节点
		List<TreeNodeModel> rootNodes = getRootNodes(nodes);
		Log.e("TAG", "转化后的所有节点个数"+nodes.size());
		Log.e("TAG", "根节点个数"+rootNodes.size());
		for (TreeNodeModel node : rootNodes) {
	//		Log.e("TAG", "根节点--"+node.getName());
			addNode(result, node, defaultExpandLevel, 1);
		}
		Log.e("TAG", "排序完成的节点个数"+result.size());
		for (TreeNodeModel node : result) {
			
		//	Log.e("TAG", "排序----"+node.getName());
		}
		return result;
	}
	
	/**
	 * 把一个节点的所有孩子节点都放入result(递归)
	 * @Title: addNode
	 * @param result					添加进哪个父节点
	 * @param node						需要添加进去的node
	 * @param defaultExpandLevel		默认展开层级
	 * @param currentLevel				当前层级
	 */
	private static void addNode(List<TreeNodeModel> result, TreeNodeModel node, int defaultExpandLevel, int currentLevel) {
		
		result.add(node);
		if (node.isLeaf()){							//如果是叶子节点说明该分支添加结束 返回
			return;
		}
		AbLogUtil.d("MYTAG", "defaultExpandLevel: "+defaultExpandLevel);
		AbLogUtil.d("MYTAG", "currentLevel: "+currentLevel);
		if (defaultExpandLevel >= currentLevel) {	//当前层级小于默认展开层级就展开当前
			node.setState("open");
			//TreeHelper.setChildNodeParentState(node, "open");
		}

		for (int i = 0; i < node.getChildrenNodeList().size(); i++) {
			addNode(result, node.getChildrenNodeList().get(i), defaultExpandLevel, currentLevel+1);
		}
	}
	
	/**
	 * 过滤出需要显示的node集合
	 * @Title: fliterVisibleNodes
	 * @return List<Node>
	 */
	public static List<TreeNodeModel> fliterVisibleNodes(List<TreeNodeModel> nodes) {
		
		List<TreeNodeModel> result = new ArrayList<TreeNodeModel>();
		for (TreeNodeModel node : nodes) {
			if("closed".equals(node.getState())){
				if(node.getChildrenNodeList() != null){
					for (TreeNodeModel temp : node.getChildrenNodeList()){
						temp.getParentNode().setState("closed");
						temp.setState("closed");
					}
				}
			}
			if (node.isRoot() || node.isParentExpend()) {	//如果当前节点是根节点或者他的父节点处于展开状态就显示
				setNodeIcon(node);	//刷新图标
				result.add(node);//显示
			}
		}
//		for (TreeNodeModel node : result) {
//			if (!node.isRoot()){
//			    if("closed".equals(node.getParentNode().getState())){
//			    	result.remove(node);
//			   }
//			}
//		}
		return result;
	}

	/**
	 * 从所有节点中获取根节点集合
	 * @Title: getRootNodes
	 * @param nodes
	 * @return List<Node> 
	 */
	private static List<TreeNodeModel> getRootNodes(List<TreeNodeModel> nodes) {
		
		List<TreeNodeModel> root = new ArrayList<TreeNodeModel>();
		
		for (TreeNodeModel node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		
		return root;
	}

	/**
	 * 给node设置图片
	 * @Title: setNodeIcon
	 * @param n void 
	 */
	private static void setNodeIcon(TreeNodeModel n) {
		if(!n.isLeaf()){//是否叶子节点
			if("closed".equals(n.getState())){
				n.setIconcls(R.drawable.fold+"");
			}else{
				n.setIconcls(R.drawable.open+"");
			}
		}else{
			if(n.isRoot()){//是否根节点
				if("closed".equals(n.getState())){
					n.setIconcls(R.drawable.fold+"");
				}else{
					n.setIconcls(R.drawable.open+"");
				}
			}
		}
	}
	

	/**
	 * @return    
	 * @Title: setChildNodeParentState   
	 * @Description: TODO(设置当前节点下的ChildNode中Parent state)   
	 * @param: @param currentNode     @param state    
	 * @return: void      
	 * @throws   
	 */
	public static TreeNodeModel setChildNodeParentState(TreeNodeModel currentNode,String state){

		List<TreeNodeModel> childNodes = currentNode.getChildrenNodeList();
		if(childNodes != null){
			if(childNodes.size() > 0){
				for (TreeNodeModel treeNodeModel : childNodes) {
					TreeNodeModel tmp = treeNodeModel.getParentNode();
					if(tmp != null){
						tmp.setState(state);
						treeNodeModel.setParentNode(tmp);
					}
				}
			}
		}
		return currentNode;
	}
	
}
