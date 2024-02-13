package com.as.filesearch.base.entity;

public class PaginationEntity {
	//开始的位置
	private int startNum;
	//当前页面页码
	private int pageSize;
	//搜索的关键字
	private String searchKeyWords ;
	//排序的字段
	private String orderBy ;
	//排序的方向
	private String ascOrDesc;
	

	public String getSearchKeyWords() {
		return searchKeyWords;
	}


	public void setSearchKeyWords(String searchKeyWords) {
		this.searchKeyWords = searchKeyWords;
	}


	public PaginationEntity(int currentPage,int pageSize,String searchKeyWords,String orderBy,String ascOrDesc){
		this.pageSize = pageSize;
		this.startNum = (currentPage -1) * pageSize;
		this.searchKeyWords = searchKeyWords;
		this.orderBy = orderBy;
		this.ascOrDesc = ascOrDesc;
	}


	public int getStartNum() {
		return startNum;
	}


	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public String getAscOrDesc() {
		return ascOrDesc;
	}


	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}


	
}
