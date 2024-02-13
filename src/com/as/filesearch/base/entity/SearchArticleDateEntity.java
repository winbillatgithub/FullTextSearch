package com.as.filesearch.base.entity;

public class SearchArticleDateEntity  implements Comparable{
	
	private String articleDateStr ;
	
	private long articleDateCount;

	public String getArticleDateStr() {
		return articleDateStr;
	}

	public void setArticleDateStr(String articleDateStr) {
		this.articleDateStr = articleDateStr;
	}

	public long getArticleDateCount() {
		return articleDateCount;
	}

	public void setArticleDateCount(long articleDateCount) {
		this.articleDateCount = articleDateCount;
	}

	@Override
	public int compareTo(Object otherObject) {
		SearchArticleDateEntity searchArticleDateEntity = (SearchArticleDateEntity) otherObject;
		Long articleDateCount_Other = Long.valueOf(searchArticleDateEntity.getArticleDateStr());
		Long articleDateCount_Self = Long.valueOf(this.getArticleDateStr());
		//return articleDateCount_Self.compareTo(articleDateCount_Other);
		return articleDateCount_Other.compareTo(articleDateCount_Self);

	}

}
