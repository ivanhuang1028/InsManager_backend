package com.hl.insmanager.vo.tree;

import java.util.Comparator;

/**
 * 节点比较器
 */
public class NodeIDComparator implements Comparator {
	// 按照节点编号比较
	public int compare(Object o1, Object o2) {
		int j1 = Integer.parseInt(((Node) o1).resource_id);
		int j2 = Integer.parseInt(((Node) o2).resource_id);
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}