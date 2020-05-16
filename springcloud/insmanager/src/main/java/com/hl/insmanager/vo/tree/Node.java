package com.hl.insmanager.vo.tree;

/**
 * 节点类
 */
public class Node {
	/**
	 * 节点编号
	 */
	// public String id;
	public String resource_id;
	/**
	 * 节点内容
	 */
	// public String text;
	/**
	 * 节点内容
	 */
	public String resource_name;
	// 资源全名名称
	public String resource_fullname;

	public String resource_desc;
	public String url;
	public String type;
	public String resourcepid;
	public String resourcepids;
	/**
	 * 父节点编号
	 */
	public String parentId;
	/**
	 * 孩子节点列表
	 */
	private Children children = new Children();

	// 先序遍历，拼接JSON字符串
	public String toString() {
		String result = "{" + "resource_id : '" + resource_id + "'"
				+ ", resource_name : '" + resource_name + "'"
				+ ", resource_fullname : '" + resource_fullname + "'"
				+ ", resource_desc : '" + resource_desc + "'" + ", url : '"
				+ url + "'" + ", type : '" + type + "'" + ", resourcepid : '"
				+ resourcepid + "'" + ", resourcepids : '" + resourcepids + "'";

		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		} else {
			result += ", leaf : true";
		}

		return result + "}";
	}

	// 兄弟节点横向排序
	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

	// 添加孩子节点
	public void addChild(Node node) {
		this.children.addChild(node);
	}
}
