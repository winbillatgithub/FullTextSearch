package com.as.filesearch.controller.admin.files;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.base.helper.SolrClientHelper;
import com.as.filesearch.entity.KeywordEntity;
import com.as.filesearch.entity.LabelEntity;
import com.as.filesearch.entity.SysFileMeta;
import com.as.filesearch.service.admin.files.IKeywordEntityService;
import com.as.filesearch.service.admin.files.ILabelEntityService;

@Controller
@RequestMapping(value = "/admin/files/labelEntityController")
public class LabelEntityController  extends AdminBaseController {

	// Newly found key words
	private String newKeyWords = "";
	String dicPath = "";

	@Resource(name = "LabelEntityService")
	private ILabelEntityService service;

	@Resource(name = "KeywordEntityService")
	private IKeywordEntityService keywordEntityService;

	@RequestMapping(value = "getLabelView")
	public ModelAndView getCourtView(String readonly, String label) {
		AdminBaseController.baseLogger.trace("getLabelView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_label");
		modelAndView.addObject("readonly", readonly);
		modelAndView.addObject("label", label);

		this.dicPath = this.getContextValueByKey("dic_path");

		AdminBaseController.baseLogger.trace("getLabelView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "getHzNumberView")
	public ModelAndView getHzNumberView(String readonly, String label) {
		AdminBaseController.baseLogger.trace("getHzNumberView() ==>");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_hznumber");
		modelAndView.addObject("readonly", readonly);
		modelAndView.addObject("label", label);

		this.dicPath = this.getContextValueByKey("dic_path");

		AdminBaseController.baseLogger.trace("getHzNumberView() <==");
		return modelAndView;
	}
	@RequestMapping(value = "getLabelInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getLabelInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getLabelInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortItem", "type,name");
		paramMap.put("sortOrder", "asc");
		paramMap.put("type", type);
		paramMap.put("name", name);
		Map<String,Object> paginationMap = service.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getLabelInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}

	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveData(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveData() ==>");

		//String pDate = request.getParameter("pDate");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		List<LabelEntity> riInserted = null;
		List<LabelEntity> riUpdated = null;
		List<LabelEntity> riDeleted = null;

		int userId = 0;
		Date currentDate = new Date();

		try {
			if (inserted != null) {
				riInserted = JsonHelper.jsonToBeanList(String.valueOf(inserted), LabelEntity.class);
				// 调用插入方法
				for (int index = 0; index < riInserted.size(); index++) {
					LabelEntity ri = riInserted.get(index);
					ri.setCreateId(userId);
					ri.setCreateTime(currentDate);
					service.insertSelective(ri);
					// Insert into keywords table
					SysFileMeta metaData = new SysFileMeta();
					if ("1".equals(ri.getType())) {
						metaData.setHzNumber1(ri.getName());
					} else if ("0".equals(ri.getType())) {
						metaData.setKeyword1(ri.getName());
					}
					saveKeywords(metaData);
					metaData = null;
				}
			}
			if (updated != null) {
				riUpdated = JsonHelper.jsonToBeanList(String.valueOf(updated), LabelEntity.class);
				// 调用更新方法
				for (int index = 0; index < riUpdated.size(); index++) {
					LabelEntity ri = riUpdated.get(index);
					ri.setCreateId(userId);
					ri.setCreateTime(currentDate);
					service.updateByPrimaryKey(ri);
					// Insert into keywords table
					SysFileMeta metaData = new SysFileMeta();
					if ("1".equals(ri.getType())) {
						metaData.setHzNumber1(ri.getName());
					} else if ("0".equals(ri.getType())) {
						metaData.setKeyword1(ri.getName());
					}
					saveKeywords(metaData);
					metaData = null;
				}
			}
			if (deleted != null) {
				riDeleted = JsonHelper.jsonToBeanList(String.valueOf(deleted), LabelEntity.class);
				// 调用删除方法
				for (int index = 0; index < riDeleted.size(); index++) {
					LabelEntity ri = riDeleted.get(index);
//					service.deleteByPrimaryKey(ri.getId());
					ri.setCreateId(userId);
					ri.setCreateTime(currentDate);
					ri.setIsActive("0");
					service.updateByPrimaryKey(ri);
				}
			}
		} catch (Exception e) {
			return jsonResult(false, "错误信息:" + e.getMessage());
		}
		baseLogger.trace("saveData() <==");
		return jsonResult(true, "保存成功");
	}
	private  Map<String, Object> saveKeywords(SysFileMeta metaData) throws Exception {
		String newKeyWords = saveMetaData(metaData);
		//
		// Step 2, save keywords
		if (newKeyWords.length() > 1) {
			// Step 4, add keywords to ext.dic
			try {
				addKeywords2dic(newKeyWords);
			} catch(IOException e) {
				throw new Exception(e.getMessage());					
			}
			newKeyWords = newKeyWords.substring(0, newKeyWords.length()-1);

			// Step 3, add keywords to solr
			SolrClientHelper sch = new SolrClientHelper();
			try {
				boolean bret = sch.addWords("ADD", newKeyWords);
				if (bret == false) {
					throw new Exception(Constant._ADD_KEY_WORDS_FAILED_);
				}
			} catch (SolrServerException sse) {
				throw new Exception(sse.getMessage());
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}
		}
		return jsonResult(true, "");
	}
	/*
	 * Save lawfirm, lawyer, clients and judge info to db
	 */
	private String saveMetaData(SysFileMeta metaData) {
		AdminBaseController.baseLogger.trace("saveMetaData() ==>");
		newKeyWords = "";
		//
		// Compare with the label table
		if (metaData.getKeyword1() != null && !"".equals(metaData.getKeyword1())) {
			newKeyWords += metaData.getKeyword1();
			newKeyWords += ",";
		}
		if (metaData.getKeyword2() != null && !"".equals(metaData.getKeyword2())) {
			newKeyWords += metaData.getKeyword2();
			newKeyWords += ",";
		}
		if (metaData.getKeyword3() != null && !"".equals(metaData.getKeyword3())) {
			newKeyWords += metaData.getKeyword3();
			newKeyWords += ",";
		}
		if (metaData.getKeyword4() != null && !"".equals(metaData.getKeyword4())) {
			newKeyWords += metaData.getKeyword4();
			newKeyWords += ",";
		}
		if (metaData.getKeyword5() != null && !"".equals(metaData.getKeyword5())) {
			newKeyWords += metaData.getKeyword5();
			newKeyWords += ",";
		}
		//
		// Compare with the huanzhu number table
		if (metaData.getHzNumber1() != null && !"".equals(metaData.getHzNumber1())) {
			newKeyWords += metaData.getHzNumber1();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber2() != null && !"".equals(metaData.getHzNumber2())) {
			newKeyWords += metaData.getHzNumber2();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber3() != null && !"".equals(metaData.getHzNumber3())) {
			newKeyWords += metaData.getHzNumber3();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber4() != null && !"".equals(metaData.getHzNumber4())) {
			newKeyWords += metaData.getHzNumber4();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber5() != null && !"".equals(metaData.getHzNumber5())) {
			newKeyWords += metaData.getHzNumber5();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber6() != null && !"".equals(metaData.getHzNumber6())) {
			newKeyWords += metaData.getHzNumber6();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber7() != null && !"".equals(metaData.getHzNumber7())) {
			newKeyWords += metaData.getHzNumber7();
			newKeyWords += ",";
		}
		if (metaData.getHzNumber8() != null && !"".equals(metaData.getHzNumber8())) {
			newKeyWords += metaData.getHzNumber8();
			newKeyWords += ",";
		}
		//
		// Compare with the keyword table
		newKeyWords = insert2keyword(newKeyWords, "1");

		AdminBaseController.baseLogger.trace("saveMetaData() <== returns true");
		return newKeyWords;
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
			int count = service.selectAllCount(paramMap);
			if (count == 0) {
				LabelEntity record = new LabelEntity();
				record.setIsActive("1");  // Active
				record.setType(type); // extended keyword
				record.setName(arr[i]);
				record.setCreateId(0);
				record.setCreateTime(date);
				service.insert(record);
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