package com.as.filesearch.entity;

import java.util.Date;

public class LawyerEntity {
    private Integer id;

    private Integer lawfirmId;

    private String name;

    private String isActive;

    private Date createTime;

    private Integer createId;

    private String lawfirmName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLawfirmId() {
        return lawfirmId;
    }

    public void setLawfirmId(Integer lawfirmId) {
        this.lawfirmId = lawfirmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

	public String getLawfirmName() {
		return lawfirmName;
	}

	public void setLawfirmName(String lawfirmName) {
		this.lawfirmName = lawfirmName;
	}
}