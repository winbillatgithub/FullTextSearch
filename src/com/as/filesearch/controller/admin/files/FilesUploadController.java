package com.as.filesearch.controller.admin.files;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.DateHelper;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.base.helper.SolrClientHelper;
import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.SysFileMeta;
import com.as.filesearch.entity.ClientEntity;
import com.as.filesearch.entity.CourtEntity;
import com.as.filesearch.entity.JudgeEntity;
import com.as.filesearch.entity.KeywordEntity;
import com.as.filesearch.entity.LabelEntity;
import com.as.filesearch.entity.LawFirmEntity;
import com.as.filesearch.entity.LawyerEntity;
import com.as.filesearch.entity.SysCaseCauseEntity;
import com.as.filesearch.entity.SysCaseTypeEntity;
import com.as.filesearch.entity.UploadedFileInfoEntity;
import com.as.filesearch.service.admin.files.IClientEntityService;
import com.as.filesearch.service.admin.files.ICourtEntityService;
import com.as.filesearch.service.admin.files.IJudgeEntityService;
import com.as.filesearch.service.admin.files.IKeywordEntityService;
import com.as.filesearch.service.admin.files.ILabelEntityService;
import com.as.filesearch.service.admin.files.ILawFirmEntityService;
import com.as.filesearch.service.admin.files.ILawyerEntityService;
import com.as.filesearch.service.admin.files.ISysCaseCauseEntityService;
import com.as.filesearch.service.admin.files.ISysCaseTypeEntityService;
import com.as.filesearch.service.admin.files.IUploadedFileInfoEntityService;
import com.as.filesearch.service.admin.files.ParseDocMetadata;
import com.as.filesearch.service.admin.files.ParseDocx;

@Controller
@RequestMapping(value = "/admin/files/fileController")
public class FilesUploadController  extends AdminBaseController {

	// Uploaded file path
	private String uploadFilePath = null;
	// Extend dictionary path
	private String dicPath = null;
	// Meta data ojbect
	private SysFileMeta metaData = new SysFileMeta();
	// Newly found key words
	private String newKeyWords = "";
	// Court id
	private int courtId = -1;

	// this will store uploaded files
	private static List<SysFileMeta> files = new LinkedList<SysFileMeta>();

	@Resource(name = "SysCaseCauseEntityService")
	private ISysCaseCauseEntityService sysCaseCauseService;
	@Resource(name = "SysCaseTypeEntityService")
	private ISysCaseTypeEntityService sysCaseTypeService;
	@Resource(name = "UploadedFileInfoEntityService")
	private IUploadedFileInfoEntityService uploadedFileInfoEntityService;
	@Resource(name = "CourtEntityService")
	private ICourtEntityService courtEntityService;
	@Resource(name = "ClientEntityService")
	private IClientEntityService clientEntityService;
	@Resource(name = "JudgeEntityService")
	private IJudgeEntityService judgeEntityService;
	@Resource(name = "LawFirmEntityService")
	private ILawFirmEntityService lawFirmEntityService;
	@Resource(name = "LawyerEntityService")
	private ILawyerEntityService lawyerEntityService;
	@Resource(name = "KeywordEntityService")
	private IKeywordEntityService keywordEntityService;
	@Resource(name = "LabelEntityService")
	private ILabelEntityService labelEntityService;

	@RequestMapping(value = "fileView")
	public ModelAndView returnFileView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/files_upload");

		uploadFilePath = this.getContextValueByKey("upload_path");
		dicPath = this.getContextValueByKey("dic_path");

		return modelAndView;
	}

	/***************************************************
	 * URL: /upload
	 * doPost(): upload the files and other parameters
	 ****************************************************/
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, Exception {
		AdminBaseController.baseLogger.trace("doPost() ==>");

//		try {
			//System.out.println("文件上传");
			MultipartRequestHandler mrh = new MultipartRequestHandler(this.uploadFilePath, this.uploadedFileInfoEntityService);
			Map<String, Object> result = mrh.FlashUpload(request,response);
			if (Constant._SUCCESS_ == result.get(Constant._RESULT_)) {
				String path = (String) result.get(Constant._PATH_);
				AdminBaseController.baseLogger.info("doPost() path=" + path);
				// Analyze the doc,docx file
				metaData.reset();
				ParseDocMetadata pdm = new ParseDocMetadata(path, metaData, request);
				pdm.openFile();
			} else {
//				response.setStatus(502, String.valueOf(result.get(Constant._REASON_)));
				response.sendError(501, String.valueOf(result.get(Constant._REASON_)));
				AdminBaseController.baseLogger.error("doPost() Error occurred when uploading doc file.");
				//throw new ServletException(String.valueOf(result.get(Constant._REASON_)));
			}
			mrh = null;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			AdminBaseController.baseLogger.error("doPost() <== Exception:" + e.getMessage());
//		}
		AdminBaseController.baseLogger.trace("doPost() <==");
	}

	/***************************************************
	 * URL: /getMetaData
	 * getMetaData(): upload the files and other parameters
	 ****************************************************/
	@RequestMapping(value = "getMetaData")
	public ModelAndView getMetaData(Model model, String fileId) {
		AdminBaseController.baseLogger.trace("getMetaData() ==> fileId=" + fileId);

		try {
//			SysFileMeta metaData = new SysFileMeta();
//			metaData.setTitle("标题");
			model.addAttribute("fileMetaData", metaData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AdminBaseController.baseLogger.error("getMetaData() Error Message:" + e.getMessage());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/files_meta");

		AdminBaseController.baseLogger.trace("getMetaData() <==");
		return modelAndView;
	}

	/***************************************************
	 * URL: /getCaseCause
	 * getCaseCause(): get the case types from database
	 ****************************************************/
	@RequestMapping(value = "getCaseCause")
	public void getCaseCause(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getCaseCause() ==>");
		
		List<SysCaseCauseEntity> caseCause = sysCaseCauseService.selectAllCaseCauseEntity();
		String returnString = JsonHelper.beanListToJson(caseCause);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(returnString);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnString = null;
		caseCause = null;
		AdminBaseController.baseLogger.trace("getCaseCause() <==");
	}
	
	/***************************************************
	 * URL: /getCaseCauseView
	 * getCaseCause(): get the case types from database
	 ****************************************************/
	@RequestMapping(value = "getCaseCauseView")
	public ModelAndView getCaseCauseView() {
		AdminBaseController.baseLogger.trace("getCaseCauseView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/casecausetree");

		AdminBaseController.baseLogger.trace("getCaseCauseView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getUploadedFileInfoView")
	public ModelAndView getUploadedFileInfoView() {
		AdminBaseController.baseLogger.trace("getUploadedFileInfoView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_files");

		AdminBaseController.baseLogger.trace("getUploadedFileInfoView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getLawFirmView")
	public ModelAndView getLawFirmInfoView() {
		AdminBaseController.baseLogger.trace("getLawFirmView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_lawfirm");

		AdminBaseController.baseLogger.trace("getLawFirmView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getCourtView")
	public ModelAndView getCourtView() {
		AdminBaseController.baseLogger.trace("getCourtView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_court");

		AdminBaseController.baseLogger.trace("getCourtView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getLawyerView")
	public ModelAndView getLawyerView() {
		AdminBaseController.baseLogger.trace("getLawyerView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_lawyer");

		AdminBaseController.baseLogger.trace("getLawyerView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getJudgeView")
	public ModelAndView getJudgeView() {
		AdminBaseController.baseLogger.trace("getJudgeView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_judge");

		AdminBaseController.baseLogger.trace("getJudgeView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getClientView")
	public ModelAndView getClientView() {
		AdminBaseController.baseLogger.trace("getClientView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_client");

		AdminBaseController.baseLogger.trace("getClientView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "saveUploadedFileInfo",  method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> saveUploadedFileInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response, SysFileMeta metaData) {
		AdminBaseController.baseLogger.trace("saveUploadedFileInfo() ==>");
		boolean bRet = false;
		try {
			// Restore content values froms session
			String contentHtml = String.valueOf(request.getSession().getAttribute(Constant._SESSION_CONTENT_HTML));
			String contentPlain = String.valueOf(request.getSession().getAttribute(Constant._SESSION_CONTENT_PLAIN));
			metaData.setContentPlain(contentPlain);
			metaData.setContentHtml(contentHtml);
	        if (metaData.getJudgeDate() != null && !"".equals(metaData.getJudgeDate())) {
	        	int judgeYear = Integer.parseInt(metaData.getJudgeDate().substring(0, 4));
	        	metaData.setJudgeYear(judgeYear);
	        }
			this.metaData = metaData;

			//
			// Step 1, modify extend dictionary
			newKeyWords = "";
			if (!saveMetaData(metaData)) {
				return jsonResult(false, Constant._SAVE_META_DATA_FAILED_);
			}
			//
			// Step 2, save keywords
			if (newKeyWords.length() > 1) {
				// Step 4, add keywords to ext.dic
				try {
					addKeywords2dic(newKeyWords);
				} catch(IOException e) {
					return jsonResult(false, e.getMessage());					
				}
				newKeyWords = newKeyWords.substring(0, newKeyWords.length()-1);

				// Step 3, add keywords to solr
				SolrClientHelper sch = new SolrClientHelper();
				try {
					boolean bret = sch.addWords("ADD", newKeyWords);
					if (bret == false) {
						return jsonResult(false, Constant._ADD_KEY_WORDS_FAILED_);
					}
				} catch (SolrServerException sse) {
					return jsonResult(false, "错误信息:" + sse.getMessage());
				} catch (IOException e) {
					return jsonResult(false, "错误信息:" + e.getMessage());
				}
			}
	        /*
	         * Step n-1, full text indexing
	         */
	        List<SysFileMeta> items = new ArrayList<SysFileMeta>();
	        items.add(metaData);
	        ParseDocx docx = new ParseDocx();
	        bRet = docx.IndexDocs(items);
	        docx = null;
			/*
			 * Step n, save basic file informaton to db
			 */
			UploadedFileInfoEntity fi = new UploadedFileInfoEntity();
			fi.setId(metaData.getId());
			fi.setFilename(metaData.getFileName());
			fi.setFilesize(metaData.getFileSize());
			fi.setFiletype(metaData.getFileType());
			String date = metaData.getUploadDate();
			if (date != null) {
				fi.setCreatedate(DateHelper.parseDate(date, "yyyy-MM-dd HH:mm:ss"));
			}
			uploadedFileInfoEntityService.insert(fi);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return jsonResult(false, "错误信息:" + e.getMessage());
		} catch (SolrServerException sse) {
			sse.printStackTrace();
			return jsonResult(false, "错误信息:" + sse.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return jsonResult(false, "错误信息:" + ioe.getMessage());
		}

		if (bRet) {
			return jsonResult(true, Constant._SAVE_SUCCESS_);
		}
		return jsonResult(false, Constant._SAVE_FILE_INFO_FAILED_);
	}

	@RequestMapping(value = "getUploadedFileInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getUploadedFileInfo(HttpServletRequest request, HttpServletResponse response,
			String id, String fileName, String fileType) {
		AdminBaseController.baseLogger.trace("getUploadedFileInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("pageSize", pageSize);
		paramMap.put("id", id);
		paramMap.put("fileName", fileName);
		paramMap.put("fileType", fileType);
//		paramMap.put("accurate", "");
		Map<String,Object> paginationMap = uploadedFileInfoEntityService.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getUploadedFileInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}

	@RequestMapping(value = "getLawFirmInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getLawFirmInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getLawFirmInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String name = request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortItem", "name");
		paramMap.put("sortOrder", "asc");
		paramMap.put("name", name);
		Map<String,Object> paginationMap = lawFirmEntityService.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getLawFirmInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}

	@RequestMapping(value = "getLawyerInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getLawyerInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getLawyerInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String name = request.getParameter("name");
		String lawfirmname = request.getParameter("lawfirmname");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortItem", "name");
		paramMap.put("sortOrder", "asc");
		paramMap.put("name", name);
		paramMap.put("lawfirmname", lawfirmname);
		Map<String,Object> paginationMap = lawyerEntityService.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getLawyerInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}
	@RequestMapping(value = "getJudgeInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getJudgeInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getJudgeInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String courtId = request.getParameter("courtId");
		String name = request.getParameter("name");
		String courtname = request.getParameter("courtname");
		String type = request.getParameter("type");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortItem", "name");
		paramMap.put("sortOrder", "asc");
		paramMap.put("courtname", courtname);
		paramMap.put("name", name);
		paramMap.put("type", type);
		Map<String,Object> paginationMap = judgeEntityService.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getJudgeInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}
	@RequestMapping(value = "getClientInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getClientInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getClientInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("name", name);
		paramMap.put("type", type);
		paramMap.put("sortItem", "type, name");
		paramMap.put("sortOrder", "asc");
		Map<String,Object> paginationMap = clientEntityService.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getClientInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}

	@RequestMapping(value = "validateCaseCause",  method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> validateCaseCause(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("validateCaseCause() ==>");
		String fullName = null;
		try {
			String caseCause = request.getParameter("val");
			AdminBaseController.baseLogger.trace("caseCause=" + caseCause);
			// Whether the caseCause maintained in db
			List<SysCaseCauseEntity> caseCauseEntityList = sysCaseCauseService.selectByName(caseCause);
			if (caseCauseEntityList.size() > 0) {
				fullName = caseCauseEntityList.get(0).getName();
			} else {
				return jsonResult(false, Constant._NOT_FOUND_CASE_CAUSE_);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AdminBaseController.baseLogger.error("validateCaseCause() <==. Cause:" + e.getMessage());
			return jsonResult(false, e.getMessage());
		}

		AdminBaseController.baseLogger.trace("validateCaseCause() <==, returns {true," + fullName + "}");
		return jsonResult(true, fullName);
	}

	@RequestMapping(value = "validateCourt",  method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> validateCourt(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("validateCourt() ==>");
		String fullName = null;
		try {
			String court = request.getParameter("val");
			AdminBaseController.baseLogger.trace("court=" + court);
			// Whether the court maintained in db
			List<CourtEntity> courtEntityList = courtEntityService.selectByFullName(court);
			if (courtEntityList.size() > 0) {
				fullName = courtEntityList.get(0).getFullName();
				this.courtId = courtEntityList.get(0).getId();
			} else {
				return jsonResult(false, Constant._NOT_FOUND_COURT_);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AdminBaseController.baseLogger.error("validateCourt() <==. Cause:" + e.getMessage());
			return jsonResult(false, e.getMessage());
		}

		AdminBaseController.baseLogger.trace("validateCourt() <==, returns {true," + fullName + "}");
		return jsonResult(true, fullName);
	}

	@RequestMapping(value = "validateCaseType",  method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> validateCaseType(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("validateCaseType() ==>");
		String fullName = null;
		try {
			String caseType = request.getParameter("val");
			AdminBaseController.baseLogger.trace("caseType=" + caseType);
			// Whether the caseType maintained in db
			List<SysCaseTypeEntity> caseTypeEntityList = sysCaseTypeService.selectByName(caseType);
			if (caseTypeEntityList.size() > 0) {
				fullName = caseTypeEntityList.get(0).getName();
			} else {
				return jsonResult(false, Constant._NOT_FOUND_CASE_TYPE_);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AdminBaseController.baseLogger.error("validateCaseType() <==. Cause:" + e.getMessage());
			return jsonResult(false, e.getMessage());
		}

		AdminBaseController.baseLogger.trace("validateCaseType() <==, returns {true," + fullName + "}");
		return jsonResult(true, fullName);
	}
	
	/*
	 * Save lawfirm, lawyer, clients and judge info to db
	 */
	private boolean saveMetaData(SysFileMeta metaData) {
		AdminBaseController.baseLogger.trace("saveMetaData() ==>");
		Date date = new Date();
		newKeyWords = "";
		//
		// Insert into c_dic_lawfirm table
		if (metaData.getLawFirm() != null && !"".equals(metaData.getLawFirm())) {
			String arrLawfirm[] = metaData.getLawFirm().split(",");
			for(int index = 0; index < arrLawfirm.length; index ++) {
				int id = -1; // Lawfirm id
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("name", arrLawfirm[index]);
				paramMap.put("accurate", 1);
				int count = lawFirmEntityService.selectAllCount(paramMap);
				if (count == 0) {
					LawFirmEntity record = new LawFirmEntity();
					record.setIsActive("1");  // Active
					record.setName(arrLawfirm[index]);
					record.setCreateId(0);
					record.setCreateTime(date);
					lawFirmEntityService.insert(record);
					// add keyword
					newKeyWords += arrLawfirm[index];
					newKeyWords += ",";
					AdminBaseController.baseLogger.info("saveMetaData() Added lawfirm:" + arrLawfirm[index]);
				}
				List<LawFirmEntity> list = lawFirmEntityService.selectByName(arrLawfirm[index]);
				if (list.size() > 0) {
					id = list.get(0).getId();
				} else {
					AdminBaseController.baseLogger.error("saveMetaData() <== Not found lawfirm:" + arrLawfirm[index]);
					return false;
				}
				//
				// Insert into c_dic_lawyer table
				if (metaData.getLawyers() != null && !"".equals(metaData.getLawyers())) {
					String lawyers = metaData.getLawyers();
					String[] arr = lawyers.split(",");
					if (arr.length >= arrLawfirm.length) {
						paramMap.put("lawfirmId", id);
						paramMap.put("name", arr[index]);
						paramMap.put("accurate", 1);
						count = lawyerEntityService.selectAllCount(paramMap);
						if (count == 0) {
							LawyerEntity record = new LawyerEntity();
							record.setIsActive("1");  // Active
							record.setLawfirmId(id);
							record.setName(arr[index]);
							record.setCreateId(0);
							record.setCreateTime(date);
							lawyerEntityService.insert(record);
							// add keyword
							newKeyWords += arr[index];
							newKeyWords += ",";
							AdminBaseController.baseLogger.trace("saveMetaData() == Added lawyer:" + arr[index]);
						}
					}
				} else {
					AdminBaseController.baseLogger.trace("Lawyers are empty");
				}
				paramMap = null;
			}
		} else {
			AdminBaseController.baseLogger.trace("Lawfirm is empty");
		}
		//
		// Chief Judge
//		courtEntityService.selectByFullName(metaData.getCourt());
		insert2judge(metaData.getChiefJudge(), "0", date);

		// Judges
		insert2judge(metaData.getJudge(), "1", date);

		// Clerks
		insert2judge(metaData.getClerk(), "2", date);

		// Excutor
		insert2client(metaData.getExcutor(), "0", date);

		// Excuted
		insert2client(metaData.getExcuted(), "1", date);

		// Third part
		insert2client(metaData.getThirdPart(), "2", date);

		// Legal person
		insert2client(metaData.getLegalPerson(), "3", date);

		//
		// Add court to ext.dic
		newKeyWords += metaData.getCourt();
		newKeyWords += ",";
		//
		// Add cause to ext.dic
		newKeyWords += metaData.getCause();
		newKeyWords += ",";
		//
		// Compare with the label table
		String newLables = "";
		if (metaData.getKeyword1() != null && !"".equals(metaData.getKeyword1())) {
			newLables += metaData.getKeyword1();
			newLables += ",";
		}
		if (metaData.getKeyword2() != null && !"".equals(metaData.getKeyword2())) {
			newLables += metaData.getKeyword2();
			newLables += ",";
		}
		if (metaData.getKeyword3() != null && !"".equals(metaData.getKeyword3())) {
			newLables += metaData.getKeyword3();
			newLables += ",";
		}
		if (metaData.getKeyword4() != null && !"".equals(metaData.getKeyword4())) {
			newLables += metaData.getKeyword4();
			newLables += ",";
		}
		if (metaData.getKeyword5() != null && !"".equals(metaData.getKeyword5())) {
			newLables += metaData.getKeyword5();
			newLables += ",";
		}
		if (newLables.length() > 0) {
			metaData.setLables(newLables);
		}
		newKeyWords += insert2label(newLables, "0");
		//
		// Compare with the huanzhu number table
		String newHzNumbers = "";
		if (metaData.getHzNumber1() != null && !"".equals(metaData.getHzNumber1())) {
			newHzNumbers += metaData.getHzNumber1();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber2() != null && !"".equals(metaData.getHzNumber2())) {
			newHzNumbers += metaData.getHzNumber2();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber3() != null && !"".equals(metaData.getHzNumber3())) {
			newHzNumbers += metaData.getHzNumber3();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber4() != null && !"".equals(metaData.getHzNumber4())) {
			newHzNumbers += metaData.getHzNumber4();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber5() != null && !"".equals(metaData.getHzNumber5())) {
			newHzNumbers += metaData.getHzNumber5();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber6() != null && !"".equals(metaData.getHzNumber6())) {
			newHzNumbers += metaData.getHzNumber6();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber7() != null && !"".equals(metaData.getHzNumber7())) {
			newHzNumbers += metaData.getHzNumber7();
			newHzNumbers += ",";
		}
		if (metaData.getHzNumber8() != null && !"".equals(metaData.getHzNumber8())) {
			newHzNumbers += metaData.getHzNumber8();
			newHzNumbers += ",";
		}
		if (newHzNumbers.length() > 0) {
			metaData.setHzNumbers(newHzNumbers);
		}
		newKeyWords += insert2label(newHzNumbers, "1");

		//
		// Compare with the keyword table
		newKeyWords = insert2keyword(newKeyWords, "1");

		AdminBaseController.baseLogger.trace("saveMetaData() <== returns true");
		return true;
	}
	/*
	 * Add new keywords into extend dictionary
	 */
	private boolean addKeywords2dic(String keyWords) throws IOException {

		String formattedWords = keyWords.replaceAll(",", "\n");
        // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(dicPath, true),"UTF-8"
				));   
		out.write(formattedWords);
		out.close();

        return true;
	}
	/*
	 * Insert records into c_dic_client
	 */
	private void insert2client(String str, String type, Date date) {
		AdminBaseController.baseLogger.trace("insert2client() ==> str=" + str + ",type=" + type);
		if (str == null || "".equals(str)) {
			AdminBaseController.baseLogger.trace("insert2client() <== str is null.");
			return;
		}
		String[] arr = str.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i ++) {
			paramMap.put("type", type);
			paramMap.put("name", arr[i]);
			paramMap.put("accurate", 1);
			int count = clientEntityService.selectAllCount(paramMap);
			if (count == 0) {
				ClientEntity record = new ClientEntity();
				record.setIsActive("1");  // Active
				record.setType(type); // Excuted
				record.setName(arr[i]);
				record.setCreateId(0);
				record.setCreateTime(date);
				clientEntityService.insert(record);
				// add keyword
				newKeyWords += arr[i];
				newKeyWords += ",";
				AdminBaseController.baseLogger.debug("insert2client() == Keywords added:" + arr[i]);
			}
		}
		paramMap = null;
		AdminBaseController.baseLogger.trace("insert2client() <== returns:" + newKeyWords);
	}
	
	/*
	 * Insert records into c_dic_judge
	 */
	private void insert2judge(String str, String type, Date date) {
		AdminBaseController.baseLogger.trace("insert2judge() ==> str=" + str + ",type=" + type);
		if (str == null || "".equals(str)) {
			AdminBaseController.baseLogger.trace("insert2judge() <== str is null.");
			return;
		}
		String[] arr = str.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i ++) {
			paramMap.put("courtId", courtId);
			paramMap.put("type", type);
			paramMap.put("name", arr[i]);
			paramMap.put("accurate", 1);
			int count = judgeEntityService.selectAllCount(paramMap);
			if (count == 0) {
				JudgeEntity record = new JudgeEntity();
				record.setIsActive("1");  // Active
				record.setCourtId(courtId);
				record.setType(type); // Clerk
				record.setName(arr[i]);
				record.setCreateId(0);
				record.setCreateTime(date);
				judgeEntityService.insert(record);
				// add keyword
				newKeyWords += arr[i];
				newKeyWords += ",";
				AdminBaseController.baseLogger.debug("insert2judge() == Keywords added:" + arr[i]);
			}
		}
		paramMap = null;
		AdminBaseController.baseLogger.trace("insert2judge() <== returns:" + newKeyWords);
	}

	/*
	 * Insert records into c_dic_keyword
	 */
	private String insert2keyword(String str, String type) {
		AdminBaseController.baseLogger.trace("insert2keyword() ==> str=" + str + ",type=" + type);
		String keywords = "";
		if (str == null || "".equals(str)) {
			AdminBaseController.baseLogger.trace("insert2keyword() <== str is null.");
			return keywords;
		}

		Date date = new Date();
		String[] arr = str.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i ++) {
			paramMap.put("name", arr[i]);
			int count = keywordEntityService.selectAllCount(paramMap);
			if (count == 0) {
				KeywordEntity record = new KeywordEntity();
				record.setIsActive("1");  // Active
				record.setType(type); // extended keyword
				record.setName(arr[i]);
				record.setCreateId(0);
				record.setCreateTime(date);
				keywordEntityService.insert(record);
				// add keyword
				keywords += arr[i];
				keywords += ",";
			}
		}
		paramMap = null;
		date = null;
		AdminBaseController.baseLogger.trace("insert2keyword() <==");
		return keywords;
	}
	/*
	 * Insert records into c_dic_label
	 * type:  0:分组标签，1:环助码
	 */
	private String insert2label(String str, String type) {
		AdminBaseController.baseLogger.trace("insert2label() ==> str=" + str + ",type=" + type);
		String keywords = "";
		if (str == null || "".equals(str)) {
			AdminBaseController.baseLogger.trace("insert2label() <== str is null.");
			return keywords;
		}

		Date date = new Date();
		String[] arr = str.split(",");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < arr.length; i ++) {
			paramMap.put("name", arr[i]);
			paramMap.put("type", type);
			paramMap.put("accurate", '1');
			int count = labelEntityService.selectAllCount(paramMap);
			if (count == 0) {
				LabelEntity record = new LabelEntity();
				record.setIsActive("1");  // Active
				record.setType(type); // extended keyword
				record.setName(arr[i]);
				record.setCreateId(0);
				record.setCreateTime(date);
				labelEntityService.insert(record);
				// add keyword
				keywords += arr[i];
				keywords += ",";
			}
		}
		paramMap = null;
		date = null;
		AdminBaseController.baseLogger.trace("insert2label() <==");
		return keywords;
	}
}