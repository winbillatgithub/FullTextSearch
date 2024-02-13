package com.as.filesearch.entity;

import java.io.InputStream;

public class SysFileMeta {

	/*
	 * 文书内容相关元数据信息
	 */
	// 唯一标识 
	private String id;//
	// 指导案例标志 
	private boolean guideCase;//
	// 标题 
	private String title;//
	// 案号 
	private String caseNo;//
	// 法庭 
	private String court;//
	// 宣判日期 
	private String judgeDate;//
	// 宣判年份
	private int judgeYear;
	// 创建日期 
	private String createDate;//
	// 修改日期 
	private String modifyDate;//
	// 案由 环境保护行政管理(环保) | 污染环境罪
	private String cause;//
	// 类型 起诉书 | 辩护意见 | 代理意见 | 专家证人 | 鉴定文书 
	private String type;//
	// 程序 刑事一审 | 终审 
	private String step;//
	// 案件相关人员 
	// 申请执行人 (多个，分割)
	private String excutor;//
	// 申请被执行人(多个，分割) 
	private String excuted;//
	// 审判长
	private String chiefJudge;//
	// 审判员 (多个，分割)
	private String judge;//
	// 书记员 (多个，分割)
	private String clerk;//
	//委托代理人(多个，分割)
	private String authorizedAgent;//
	//法定代理人(多个，分割)
	private String legalPerson;//
	//第三人(多个，分割)
	private String thirdPart;//
	// 内容 S
	private String contentIndex;	// indexed = true && stored = false
	private String contentHtml;		// indexed = false && stored = true
	private String contentPlain;	// indexed = false && stored = true
//	private String contentJason;
//	private String contentXml;
	// 律师事务所
	private String lawFirm;
	// 律师
	private String lawyers;

	/*
	 * 文件本身属性信息
	 */
	private String fileName;
	private Integer fileSize;
	private String fileType;
	private String uploadDate;

	/*
	 * 扩展字段
	 * 钥匙码，标签
	 */
	private String keyNumber;			// 钥匙码
	private String viceTitle;			// 副标题
	private String briefIntroduction;	// 案例简介
	private String judgeResult;			// 法院认为，判决结果
	private String rules;				// 法律法规
	private String indexes;				// 索引
	private String lables;				// 分组标签，逗号分隔  keywords
	private String keyword1;			// 分组标签
	private String keyword2;			// 分组标签
	private String keyword3;			// 分组标签
	private String keyword4;			// 分组标签
	private String keyword5;			// 分组标签
	private String hzNumbers;			// 环助码，逗号分隔 tags
	private String hzNumber1;			// 环助码
	private String hzNumber2;			// 环助码
	private String hzNumber3;			// 环助码
	private String hzNumber4;			// 环助码
	private String hzNumber5;			// 环助码
	private String hzNumber6;			// 环助码
	private String hzNumber7;			// 环助码
	private String hzNumber8;			// 环助码
	private String hzNumber9;			// 环助码
	private String hzNumber10;			// 环助码

	private String rsvsmart;			// 保留字段-多值-smart
	private String rsvnosmart;			// 保留字段-多值-nosmart
	private String rsv0;				// 保留字段-多值-smart
	private String rsv1;				// 保留字段-多值-smart
	private String rsv2;				// 保留字段-多值-smart
	private String rsv3;				// 保留字段-多值-smart
	private String rsv4;				// 保留字段-多值-smart
	private String rsv5;				// 保留字段-多值-nosmart
	private String rsv6;				// 保留字段-多值-nosmart
	private String rsv7;				// 保留字段-多值-nosmart
	private String rsv8;				// 保留字段-多值-nosmart
	private String rsv9;				// 保留字段-多值-nosmart

	private InputStream content;

	
	@Override
	public String toString() {
		return "FileMeta [fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + "]";
	}

	public void reset() {
		this.id = null;//
		this.guideCase = false;//
		this.title = null;//
		this.caseNo = null;//
		this.court = null;//
		this.judgeDate = null;//
		this.judgeYear = 0;
		this.createDate = null;//
		this.modifyDate = null;//
		this.cause = null;//
		this.type = null;//
		this.step = null;//
		this.excutor = null;//
		this.excuted = null;//
		this.chiefJudge = null;//
		this.judge = null;//
		this.clerk = null;//
		this.authorizedAgent = null;//
		this.legalPerson = null;//
		this.contentIndex = null;	// indexed = true && stored = false
		this.contentHtml = null;		// indexed = false && stored = true
		this.contentPlain = null;	// indexed = false && stored = true
//		this.contentJason = null;
//		this.contentXml = null;
		this.lawFirm = null;
		this.lawyers = null;
		this.fileName = null;
		this.fileSize = 0;
		this.fileType = null;
		this.uploadDate = null;
		
		this.keyNumber = null;
		this.viceTitle = null;
		this.briefIntroduction = null;
		this.judgeResult = null;	
		this.rules = null;
		this.lables = null;
		this.indexes = null;
		this.keyword1 = null;
		this.keyword2 = null;
		this.keyword3 = null;
		this.keyword4 = null;
		this.keyword5 = null;
		this.rsv0 = null;
		this.rsv1 = null;
		this.rsv2 = null;
		this.rsv3 = null;
		this.rsv4 = null;
		this.rsv5 = null;
		this.rsv6 = null;
		this.rsv7 = null;
		this.rsv8 = null;
		this.rsv9 = null;
		this.rsvsmart = null;
		this.rsvnosmart = null;
		this.hzNumbers = null;
		this.hzNumber10 = null;
		this.hzNumber1 = null;
		this.hzNumber2 = null;
		this.hzNumber3 = null;
		this.hzNumber4 = null;
		this.hzNumber5 = null;
		this.hzNumber6 = null;
		this.hzNumber7 = null;
		this.hzNumber8 = null;
		this.hzNumber9 = null;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean isGuideCase() {
		return guideCase;
	}


	public void setGuideCase(boolean guideCase) {
		this.guideCase = guideCase;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCaseNo() {
		return caseNo;
	}


	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}


	public String getCourt() {
		return court;
	}


	public void setCourt(String court) {
		this.court = court;
	}


	public String getJudgeDate() {
		return judgeDate;
	}


	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
	}


	public int getJudgeYear() {
		return judgeYear;
	}


	public void setJudgeYear(int judgeYear) {
		this.judgeYear = judgeYear;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}


	public String getCause() {
		return cause;
	}


	public void setCause(String cause) {
		this.cause = cause;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStep() {
		return step;
	}


	public void setStep(String step) {
		this.step = step;
	}


	public String getExcutor() {
		return excutor;
	}


	public void setExcutor(String excutor) {
		this.excutor = excutor;
	}


	public String getExcuted() {
		return excuted;
	}


	public void setExcuted(String excuted) {
		this.excuted = excuted;
	}


	public String getChiefJudge() {
		return chiefJudge;
	}


	public void setChiefJudge(String chiefJudge) {
		this.chiefJudge = chiefJudge;
	}


	public String getJudge() {
		return judge;
	}


	public void setJudge(String judge) {
		this.judge = judge;
	}


	public String getClerk() {
		return clerk;
	}


	public void setClerk(String clerk) {
		this.clerk = clerk;
	}


	public String getContentIndex() {
		return contentIndex;
	}


	public void setContentIndex(String contentIndex) {
		this.contentIndex = contentIndex;
	}


	public String getContentHtml() {
		return contentHtml;
	}


	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}


	public String getContentPlain() {
		return contentPlain;
	}


	public void setContentPlain(String contentPlain) {
		this.contentPlain = contentPlain;
	}


	public String getLawFirm() {
		return lawFirm;
	}


	public void setLawFirm(String lawfirm) {
		this.lawFirm = lawfirm;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Integer getFileSize() {
		return fileSize;
	}


	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public InputStream getContent() {
		return content;
	}


	public void setContent(InputStream content) {
		this.content = content;
	}

	public String getLawyers() {
		return lawyers;
	}

	public void setLawyers(String lawyers) {
		this.lawyers = lawyers;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getAuthorizedAgent() {
		return authorizedAgent;
	}

	public void setAuthorizedAgent(String authorizedAgent) {
		this.authorizedAgent = authorizedAgent;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getThirdPart() {
		return thirdPart;
	}

	public void setThirdPart(String thirdPart) {
		this.thirdPart = thirdPart;
	}

	public String getKeyNumber() {
		return keyNumber;
	}

	public void setKeyNumber(String keyNumber) {
		this.keyNumber = keyNumber;
	}

	public String getViceTitle() {
		return viceTitle;
	}

	public void setViceTitle(String viceTitle) {
		this.viceTitle = viceTitle;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroducton(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getJudgeResult() {
		return judgeResult;
	}

	public void setJudgeResult(String judgeResult) {
		this.judgeResult = judgeResult;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getIndexes() {
		return indexes;
	}

	public void setIndexes(String indexes) {
		this.indexes = indexes;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public String getKeyword5() {
		return keyword5;
	}

	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
	}

	public String getRsv0() {
		return rsv0;
	}

	public void setRsv0(String rsv0) {
		this.rsv0 = rsv0;
	}

	public String getRsv1() {
		return rsv1;
	}

	public void setRsv1(String rsv1) {
		this.rsv1 = rsv1;
	}

	public String getRsv2() {
		return rsv2;
	}

	public void setRsv2(String rsv2) {
		this.rsv2 = rsv2;
	}

	public String getRsv3() {
		return rsv3;
	}

	public void setRsv3(String rsv3) {
		this.rsv3 = rsv3;
	}

	public String getRsv4() {
		return rsv4;
	}

	public void setRsv4(String rsv4) {
		this.rsv4 = rsv4;
	}

	public String getRsv5() {
		return rsv5;
	}

	public void setRsv5(String rsv5) {
		this.rsv5 = rsv5;
	}

	public String getRsv6() {
		return rsv6;
	}

	public void setRsv6(String rsv6) {
		this.rsv6 = rsv6;
	}

	public String getRsv7() {
		return rsv7;
	}

	public void setRsv7(String rsv7) {
		this.rsv7 = rsv7;
	}

	public String getRsv8() {
		return rsv8;
	}

	public void setRsv8(String rsv8) {
		this.rsv8 = rsv8;
	}

	public String getRsv9() {
		return rsv9;
	}

	public void setRsv9(String rsv9) {
		this.rsv9 = rsv9;
	}

	public String getLables() {
		return lables;
	}

	public void setLables(String lables) {
		this.lables = lables;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getHzNumbers() {
		return hzNumbers;
	}

	public void setHzNumbers(String hzNumbers) {
		this.hzNumbers = hzNumbers;
	}

	public String getHzNumber1() {
		return hzNumber1;
	}

	public void setHzNumber1(String hzNumber1) {
		this.hzNumber1 = hzNumber1;
	}

	public String getHzNumber2() {
		return hzNumber2;
	}

	public void setHzNumber2(String hzNumber2) {
		this.hzNumber2 = hzNumber2;
	}

	public String getHzNumber3() {
		return hzNumber3;
	}

	public void setHzNumber3(String hzNumber3) {
		this.hzNumber3 = hzNumber3;
	}

	public String getHzNumber4() {
		return hzNumber4;
	}

	public void setHzNumber4(String hzNumber4) {
		this.hzNumber4 = hzNumber4;
	}

	public String getHzNumber5() {
		return hzNumber5;
	}

	public void setHzNumber5(String hzNumber5) {
		this.hzNumber5 = hzNumber5;
	}

	public String getHzNumber6() {
		return hzNumber6;
	}

	public void setHzNumber6(String hzNumber6) {
		this.hzNumber6 = hzNumber6;
	}

	public String getHzNumber7() {
		return hzNumber7;
	}

	public void setHzNumber7(String hzNumber7) {
		this.hzNumber7 = hzNumber7;
	}

	public String getHzNumber8() {
		return hzNumber8;
	}

	public void setHzNumber8(String hzNumber8) {
		this.hzNumber8 = hzNumber8;
	}

	public String getHzNumber9() {
		return hzNumber9;
	}

	public void setHzNumber9(String hzNumber9) {
		this.hzNumber9 = hzNumber9;
	}

	public String getHzNumber10() {
		return hzNumber10;
	}

	public void setHzNumber10(String hzNumber10) {
		this.hzNumber10 = hzNumber10;
	}

	public String getRsvsmart() {
		return rsvsmart;
	}

	public void setRsvsmart(String rsvsmart) {
		this.rsvsmart = rsvsmart;
	}

	public String getRsvnosmart() {
		return rsvnosmart;
	}

	public void setRsvnosmart(String rsvnosmart) {
		this.rsvnosmart = rsvnosmart;
	}
}
