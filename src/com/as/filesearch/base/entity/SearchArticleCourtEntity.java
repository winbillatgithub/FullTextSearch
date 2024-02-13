package com.as.filesearch.base.entity;

public class SearchArticleCourtEntity implements Comparable {
	
	private String courtName;
	
	private long courtCount;

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public long getCourtCount() {
		return courtCount;
	}

	public void setCourtCount(long courtCount) {
		this.courtCount = courtCount;
	}

	@Override
	public int compareTo(Object otherObject) {
		SearchArticleCourtEntity courtEntity_Other = (SearchArticleCourtEntity) otherObject;
		Long courtCount_Other = Long.valueOf( courtEntity_Other.getCourtCount());
		Long courtCount_Self = Long.valueOf( this.getCourtCount());
		//return courtCount_Self.compareTo(courtCount_Other);
		return courtCount_Other.compareTo(courtCount_Self);

	}

}
