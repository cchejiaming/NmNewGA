package com.gov.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;
import com.gongan.manage.R;

/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 *
 * @author zhy
 *
 */
public class TreeHelper {
	/**
	 * 传入我们的普通bean，转化为我们排序后的Node
	 *
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<TreeNodeCheck> getSortedNodes(List<TreeNodeCheck> datas,
													 int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException

	{
		List<TreeNodeCheck> result = new ArrayList<TreeNodeCheck>();
		// 将用户数据转化为List<Node>以及设置Node间关??
		List<TreeNodeCheck> nodes = convetData2Node(datas);
		// 拿到根节??
		List<TreeNodeCheck> rootNodes = getRootNodes(nodes);
		// 排序
		for (TreeNodeCheck node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}
		return result;
	}

	/**
	 * 过滤出所有可见的Node
	 *
	 * @param nodes
	 * @return
	 */
	public static List<TreeNodeCheck> filterVisibleNode(
			List<TreeNodeCheck> nodes) {
		List<TreeNodeCheck> result = new ArrayList<TreeNodeCheck>();

		for (TreeNodeCheck node : nodes) {
			// 如果为跟节点，或者上层目录为展开状???
			if (node.isRoot() || node.isParentExpand()) {
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * 将我们的数据转化为树的节??
	 *
	 * @param datas
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static List<TreeNodeCheck> convetData2Node(List<TreeNodeCheck> datas)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<TreeNodeCheck> nodes = new ArrayList<TreeNodeCheck>();
		TreeNodeCheck node = null;
		if (datas==null) return null;
		for (TreeNodeCheck t : datas) {
			String id = "-1";
			String pId = "-1";
			String label = null;
			boolean isParam = false;
			boolean isCheck = false;


			Class<? extends Object> clazz = t.getClass();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field f : declaredFields) {
				if (f.getAnnotation(TreeNodeId.class) != null) {
					f.setAccessible(true);
					id =(String)(f.get(t));
				}
				if (f.getAnnotation(TreeNodePid.class) != null) {
					f.setAccessible(true);
					pId =(String)( f.get(t));
				}
				if (f.getAnnotation(TreeNodeLabel.class) != null) {
					f.setAccessible(true);
					label = (String) f.get(t);
				}
				if (!id.equals("-1") && !pId.equals("-1") && label != null) {
					break;
				}
			}



			isCheck = t.isCheck();
			isParam = t.isParam();

			node = new TreeNodeCheck(id, pId, label, isCheck);
			node.setParam(isParam);
			nodes.add(node);
		}

		/**
		 * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关??
		 */
		for (int i = 0; i < nodes.size(); i++) {
			TreeNodeCheck n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				TreeNodeCheck m = nodes.get(j);
				if (m.getpId().equals(n.getId())) {
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId().equals(n.getpId())) {
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		// 设置图片
		for (TreeNodeCheck n : nodes) {
			setNodeIcon(n);
		}
		return nodes;
	}

	private static List<TreeNodeCheck> getRootNodes(List<TreeNodeCheck> nodes) {
		List<TreeNodeCheck> root = new ArrayList<TreeNodeCheck>();
		if (nodes!=null){
			for (TreeNodeCheck node : nodes) {
				if (node.isRoot())
					root.add(node);
			}
		}
		return root;
	}

	/**
	 * 把一个节点上的所有的内容都挂上去
	 */
	private static void addNode(List<TreeNodeCheck> nodes, TreeNodeCheck node,
								int defaultExpandLeval, int currentLevel) {

		nodes.add(node);
		if (defaultExpandLeval >= currentLevel) {
			node.setExpand(true);
		}

		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(nodes, node.getChildren().get(i), defaultExpandLeval,
					currentLevel + 1);
		}
	}

	/**
	 * 设置节点的图??
	 *
	 * @param node
	 */
	private static void setNodeIcon(TreeNodeCheck node) {
		if (node.getChildren().size() > 0 && node.isExpand()) {
			node.setIcon(R.drawable.tree_ex);
		} else if (node.getChildren().size() > 0 && !node.isExpand()) {
			node.setIcon(R.drawable.tree_ec);
		} else
			node.setIcon(-1);

	}

}
