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

package lyu.klt.graduationdesign.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lyu.klt.graduationdesign.bean.TreeNodeModel;

/**
 * 树形结构的帮助类
 * 
 * @author 沈龙琴
 * @version 1.0
 * @datetime 2016年7月15日 上午10:02:38
 */
public class TreeNoteUtils {

	public static List<TreeNodeModel> getTreeNodeModel(
			List<TreeNodeModel> treeNodeModels) {

		List<TreeNodeModel> treeNodeModelList = new ArrayList<TreeNodeModel>();
		List<TreeNodeModel> deptList = new ArrayList<TreeNodeModel>();
		List<TreeNodeModel> userList = new ArrayList<TreeNodeModel>();
		for (TreeNodeModel treeNodeModel : treeNodeModels) {
			if ("DEPARTMENT".equals(treeNodeModel.getNodeType())) {
				deptList.add(treeNodeModel);
			} else if ("USER".equals(treeNodeModel.getNodeType())) {
				userList.add(treeNodeModel);
			}
		}
		// 遍历部门，获取所有部门节点
		Map<String, TreeNodeModel> map = new HashMap<String, TreeNodeModel>();
		for (TreeNodeModel po : deptList) {

			TreeNodeModel node = new TreeNodeModel();
			try {
				node.setId(po.getId());
				node.setName(po.getName());
				node.setpId(po.getpId());
				node.setOrder(po.getOrder());
				node.setNodeType(po.getNodeType());
				node.setState("closed");
				addUserNode(userList, node);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			map.put(node.getId(), node);
		}

		if (!map.isEmpty()) {
			// 构造树结构
			for (Map.Entry<String, TreeNodeModel> entry : map.entrySet()) {
				TreeNodeModel node = entry.getValue();
				// 根据节点ID找到父节点
				TreeNodeModel parentNode = map.get(node.getpId());
				if (parentNode == null) {
					node.setState("closed");
					treeNodeModelList.add(node);
				} else {
					// 找到父节点的子节点列表
					List<TreeNodeModel> childrenNode = parentNode
							.getChildrenNodeList();
					TreeNodeModel parentNode1 = new TreeNodeModel();
					parentNode1.setId(parentNode.getId());
					parentNode1.setName(parentNode.getName());
					parentNode1.setpId(parentNode.getpId());
					parentNode1.setNodeType(parentNode.getNodeType());
					parentNode1.setState("closed");
					node.setParentNode(parentNode1);
					node.setState("closed");
					// 将子节点添加到父节点的子节点列表中
					childrenNode.add(node);
				}
			}
		}
		Collections.sort(treeNodeModelList, new Comparator<TreeNodeModel>() {
			public int compare(TreeNodeModel arg0, TreeNodeModel arg1) {
				return arg0.getOrder().compareTo(arg1.getOrder());
			}
		});

		return treeNodeModelList;
	}

	public static void addUserNode(List<TreeNodeModel> userList,
			TreeNodeModel node) throws Exception {
		List<TreeNodeModel> userNodeList = new ArrayList<TreeNodeModel>();
		List<TreeNodeModel> userNodes = new ArrayList<TreeNodeModel>();

		for (TreeNodeModel users : userList) {
			if(node.getId().equals(users.getpId())){
				userNodes.add(users);
			}
		}
		for (TreeNodeModel user : userNodes) {
			TreeNodeModel userNode = new TreeNodeModel();
			userNode.setId(user.getId());
			userNode.setName(user.getName());
			userNode.setpId(user.getpId());
			userNode.setNodeType(user.getNodeType());
			userNode.setState("open");

			TreeNodeModel parentNode1 = new TreeNodeModel();
			parentNode1.setId(node.getId());
			parentNode1.setName(node.getName());
			parentNode1.setpId(node.getpId());
			parentNode1.setNodeType(node.getNodeType());
			parentNode1.setState("closed");
			userNode.setParentNode(parentNode1);

			// 判断用户是否已勾选
			userNodeList.add(userNode);
		}
		node.setChildrenNodeList(userNodeList);
	}
}
