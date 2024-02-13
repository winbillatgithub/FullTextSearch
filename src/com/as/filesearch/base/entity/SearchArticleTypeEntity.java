package com.as.filesearch.base.entity;

public class SearchArticleTypeEntity implements Comparable {
	
	private String articleType ;
	
	private long articleTypeNum;

	public long getArticleTypeNum() {
		return articleTypeNum;
	}

	public void setArticleTypeNum(long articleTypeNum) {
		this.articleTypeNum = articleTypeNum;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	@Override
	public int compareTo(Object otherObject) {
		SearchArticleTypeEntity articleType_Other = (SearchArticleTypeEntity)otherObject;
		Long articleTypeNum_Other = articleType_Other.getArticleTypeNum();
		Long articleTypeNum_Self = Long.valueOf(this.articleTypeNum);
		//return articleTypeNum_Self.compareTo(articleTypeNum_Other);
		return articleTypeNum_Other.compareTo(articleTypeNum_Self);

	}

}
