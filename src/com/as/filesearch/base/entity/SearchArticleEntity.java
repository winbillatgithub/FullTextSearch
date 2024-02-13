package com.as.filesearch.base.entity;

import java.util.Date;

public class SearchArticleEntity {

	private String articleId;

	private String articleCaseNO;

	private String articleCourt;

	private String articleJudgeDate;

	private String articleTitle;

	private String articleType;
	
	private String articleReason;
	
	private String articleStep;

	public String getArticleStep() {
		return articleStep;
	}

	public void setArticleStep(String articleStep) {
		this.articleStep = articleStep;
	}

	public String getArticleReason() {
		return articleReason;
	}

	public void setArticleReason(String articleReason) {
		this.articleReason = articleReason;
	}

	private String articleContent;

	private String articleContentHtml;

	public String getArticleContentHtml() {
		return articleContentHtml;
	}

	public void setArticleContentHtml(String articleContentHtml) {
		this.articleContentHtml = articleContentHtml;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleCaseNO() {
		return articleCaseNO;
	}

	public void setArticleCaseNO(String articleCaseNO) {
		this.articleCaseNO = articleCaseNO;
	}

	public String getArticleCourt() {
		return articleCourt;
	}

	public void setArticleCourt(String articleCourt) {
		this.articleCourt = articleCourt;
	}

	public String getArticleJudgeDate() {
		return articleJudgeDate;
	}

	public void setArticleJudgeDate(String articleJudgeDate) {
		this.articleJudgeDate = articleJudgeDate;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

}
