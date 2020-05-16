package com.hl.insmanager.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Page<T> {
	/**
	 * 每页记录数
	 */
	//private int pageSize = 10;

	/**
	 * 当前页码，1开始
	 */
	//private int pageNum = 1;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 列表内容
	 */
	private List<T> list;

	public Page() {

	}

//	public Page(int pageSize, int pageNum) {
//		this.pageSize = pageSize;
//		this.pageNum = pageNum;
//	}

	public Page( long total, List<T> list) {
//		this.pageSize = pageSize;
//		this.pageNum = pageNum;
		this.total = total;
		this.list = list;
	}

	/**
	 * @return 偏移量
	 */
//	@JsonIgnore
//	public int getStart() {
//		return (this.pageNum - 1) * getPageSize();
//	}

	/**
	 * @return 总页数
	 */
//	public long getTotalPages() {
//		if (total % pageSize == 0) {
//			return total / pageSize;
//		} else {
//			return total / pageSize + 1;
//		}
//	}

//	public int getPageNum() {
//		return pageNum;
//	}
//
//	public void setPageNum(int pageNum) {
//		this.pageNum = pageNum;
//	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Page [ total=" + total + ", list="
				+ list + "]";
	}
}
