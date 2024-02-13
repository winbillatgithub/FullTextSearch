package com.as.filesearch.base.entity;

public class SearchLableEntity implements Comparable {

	private String label ;

	private long labelNum;

	@Override
	public int compareTo(Object otherObject) {
		SearchLableEntity label_Other = (SearchLableEntity)otherObject;
		Long labelNum_Other = label_Other.getLabelNum();
		Long labelNum_Self = Long.valueOf(this.labelNum);
		//return labelNum_Self.compareTo(labelNum_Other);
		return labelNum_Other.compareTo(labelNum_Self);

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(long labelNum) {
		this.labelNum = labelNum;
	}

}
