package com.cxht.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.gongan.manage.R;

/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 *
 * @author zhy
 *
 */
public class TreeHelper {
	/**
	 * �������ǵ���ͨbean��ת��Ϊ����������Node
	 *
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas,
												int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException

	{
		List<Node> result = new ArrayList<Node>();
		// ���û�����ת��ΪList<Node>�Լ�����Node���??
		List<Node> nodes = convetData2Node(datas);
		// �õ�����??
		List<Node> rootNodes = getRootNodes(nodes);
		// ����
		for (Node node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}
		return result;
	}

	/**
	 * ���˳����пɼ���Node
	 *
	 * @param nodes
	 * @return
	 */
	public static List<Node> filterVisibleNode(List<Node> nodes) {
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes) {
			// ���Ϊ���ڵ㣬�����ϲ�Ŀ¼Ϊչ��״???
			if (node.isRoot() || node.isParentExpand()) {
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * �����ǵ�����ת��Ϊ���Ľ�??
	 *
	 * @param datas
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static <T> List<Node> convetData2Node(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;

		for (T t : datas) {
			String id = "-1";
			String pId = "-1";
			String label = null;
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
			node = new Node(id, pId, label);
			nodes.add(node);
		}

		/**
		 * ����Node�䣬���ӹ�ϵ;��ÿ�����ڵ㶼�Ƚ�һ�Σ������������еĹ�??
		 */
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				Node m = nodes.get(j);
				if (m.getpId().equals(n.getId())) {
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId().equals(n.getpId())) {
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		// ����ͼƬ
		for (Node n : nodes) {
			setNodeIcon(n);
		}
		return nodes;
	}

	private static List<Node> getRootNodes(List<Node> nodes) {
		List<Node> root = new ArrayList<Node>();
		for (Node node : nodes) {
			if (node.isRoot())
				root.add(node);
		}
		return root;
	}

	/**
	 * ��һ���ڵ��ϵ����е����ݶ�����ȥ
	 */
	private static void addNode(List<Node> nodes, Node node,
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
	 * ���ýڵ��ͼ??
	 *
	 * @param node
	 */
	private static void setNodeIcon(Node node) {
		if (node.getChildren().size() > 0 && node.isExpand()) {
			node.setIcon(R.drawable.tree_ex);
		} else if (node.getChildren().size() > 0 && !node.isExpand()) {
			node.setIcon(R.drawable.tree_ec);
		} else
			node.setIcon(-1);

	}

}