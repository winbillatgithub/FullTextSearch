package com.as.filesearch.service.admin.files;

public class DocItem {
	// 唯一标识 
	String id;
	// 指导案例标志 
	boolean guidecase;
	// 标题 
	String title;
	// 案号 
	String caseno;
	// 法庭 
	String court;
	// 宣判日期 
	String judgedate;
	// 创建日期 
	String createdate;
	// 修改日期 
	String modifydate;
	// 案由 环境保护行政管理(环保) | 污染环境罪
	String cause;
	// 类型 起诉书 | 辩护意见 | 代理意见 | 专家证人 | 鉴定文书 
	String type;
	// 程序 刑事一审 | 终审 
	String step;
	// 案件相关人员 
	// 申请执行人 
	String[] excutor;
	// 申请被执行人 
	String[] excuted;
	// 审判长 
	String[] chiefjudage;
	// 审判员 
	String[] judge;
	// 书记员 
	String[] clerk;
	// 内容 
	String[] contentindex;	// indexed = true && stored = false
	String contenthtml;		// indexed = false && stored = true
	String contentplain;	// indexed = false && stored = true
//	String contentjason;
//	String contentxml;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the guidecase
	 */
	public boolean isGuidecase() {
		return guidecase;
	}
	/**
	 * @param guidecase the guidecase to set
	 */
	public void setGuidecase(boolean guidecase) {
		this.guidecase = guidecase;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the caseno
	 */
	public String getCaseno() {
		return caseno;
	}
	/**
	 * @param caseno the caseno to set
	 */
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	/**
	 * @return the court
	 */
	public String getCourt() {
		return court;
	}
	/**
	 * @param court the court to set
	 */
	public void setCourt(String court) {
		this.court = court;
	}
	/**
	 * @return the judgedate
	 */
	public String getJudgedate() {
		return judgedate;
	}
	/**
	 * @param judgedate the judgedate to set
	 */
	public void setJudgedate(String judgedate) {
		this.judgedate = judgedate;
	}
	/**
	 * @return the createdate
	 */
	public String getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	/**
	 * @return the modifydate
	 */
	public String getModifydate() {
		return modifydate;
	}
	/**
	 * @param modifydate the modifydate to set
	 */
	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}
	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}
	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}
	/**
	 * @return the excutor
	 */
	public String[] getExcutor() {
		return excutor;
	}
	/**
	 * @param excutor the excutor to set
	 */
	public void setExcutor(String[] excutor) {
		this.excutor = excutor;
	}
	/**
	 * @return the excuted
	 */
	public String[] getExcuted() {
		return excuted;
	}
	/**
	 * @param excuted the excuted to set
	 */
	public void setExcuted(String[] excuted) {
		this.excuted = excuted;
	}
	/**
	 * @return the chiefjudage
	 */
	public String[] getChiefjudage() {
		return chiefjudage;
	}
	/**
	 * @param chiefjudage the chiefjudage to set
	 */
	public void setChiefjudage(String[] chiefjudage) {
		this.chiefjudage = chiefjudage;
	}
	/**
	 * @return the judge
	 */
	public String[] getJudge() {
		return judge;
	}
	/**
	 * @param judge the judge to set
	 */
	public void setJudge(String[] judge) {
		this.judge = judge;
	}
	/**
	 * @return the clerk
	 */
	public String[] getClerk() {
		return clerk;
	}
	/**
	 * @param clerk the clerk to set
	 */
	public void setClerk(String[] clerk) {
		this.clerk = clerk;
	}
	/**
	 * @return the contentindex
	 */
	public String[] getContentindex() {
		return contentindex;
	}
	/**
	 * @param contenttext the contentindex to set
	 */
	public void setContentindex(String[] contentindex) {
		this.contentindex = contentindex;
	}
	/**
	 * @return the contenthtml
	 */
	public String getContenthtml() {
		return contenthtml;
	}
	/**
	 * @param contenthtml the contenthtml to set
	 */
	public void setContenthtml(String contenthtml) {
		this.contenthtml = contenthtml;
	}
	/**
	 * @return the contentplain
	 */
	public String getContentplain() {
		return contentplain;
	}
	/**
	 * @param contentplain the contentplain to set
	 */
	public void setContentplain(String contentplain) {
		this.contentplain = contentplain;
	}

}
