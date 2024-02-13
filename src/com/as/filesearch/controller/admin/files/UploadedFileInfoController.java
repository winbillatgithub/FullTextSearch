package com.as.filesearch.controller.admin.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.entity.SearchLableEntity;
import com.as.filesearch.base.entity.SearchTagEntity;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.base.helper.SolrClientHelper;
import com.as.filesearch.entity.SysFileMeta;
import com.as.filesearch.entity.UploadedFileInfoEntity;
import com.as.filesearch.service.admin.files.IUploadedFileInfoEntityService;

@Controller
@RequestMapping(value = "/admin/files/uploadedFileInfoController")
public class UploadedFileInfoController  extends AdminBaseController {

	@Resource(name = "UploadedFileInfoEntityService")
	private IUploadedFileInfoEntityService service;

	/***************************************************
	 * URL: /indexView
	 * getIndexData(): Get the huanzhu number and group tags
	 ****************************************************/
	@RequestMapping(value = "indexView")
	public ModelAndView returnIndexView(Model model, String articleId) {
		AdminBaseController.baseLogger.trace("returnIndexView() ==> articleId=" + articleId);

		try {
			SysFileMeta metaData = new SysFileMeta();
			metaData.setId(articleId);
			// 从索引服务器获取该文件id对应的索引数据
			SolrClientHelper solrClientHelper = new SolrClientHelper();
			Map<String, Object> queryResultMap = null;
			try {
				queryResultMap = solrClientHelper.querySolrById(null,articleId);
				List<SearchLableEntity> articleLabelList = (List<SearchLableEntity> )queryResultMap.get("articleLabelList");
				List<SearchTagEntity> articleTagList = (List<SearchTagEntity> )queryResultMap.get("articleTagList");

				if (articleLabelList != null && articleLabelList.size() > 0) {
					for (int index = 0; index < articleLabelList.size(); index ++) {
						SearchLableEntity entity = articleLabelList.get(index);
						if (index == 0) metaData.setKeyword1(entity.getLabel());
						if (index == 1) metaData.setKeyword2(entity.getLabel());
						if (index == 2) metaData.setKeyword3(entity.getLabel());
						if (index == 3) metaData.setKeyword4(entity.getLabel());
						if (index == 4) metaData.setKeyword5(entity.getLabel());
					}
				}
				if (articleTagList != null && articleTagList.size() > 0) {
					for (int index = 0; index < articleTagList.size(); index ++) {
						SearchTagEntity entity = articleTagList.get(index);
						if (index == 0) metaData.setHzNumber1(entity.getLabel());
						if (index == 1) metaData.setHzNumber2(entity.getLabel());
						if (index == 2) metaData.setHzNumber3(entity.getLabel());
						if (index == 3) metaData.setHzNumber4(entity.getLabel());
						if (index == 4) metaData.setHzNumber5(entity.getLabel());
						if (index == 5) metaData.setHzNumber6(entity.getLabel());
						if (index == 6) metaData.setHzNumber7(entity.getLabel());
						if (index == 7) metaData.setHzNumber8(entity.getLabel());
						if (index == 8) metaData.setHzNumber9(entity.getLabel());
						if (index == 9) metaData.setHzNumber10(entity.getLabel());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			model.addAttribute("fileMetaData", metaData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AdminBaseController.baseLogger.error("getMetaData() Error Message:" + e.getMessage());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/files/admin_fileindex");

		AdminBaseController.baseLogger.trace("returnIndexView() <==");
		return modelAndView;
	}

	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveData(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveData() ==>");

		//String pDate = request.getParameter("pDate");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		List<UploadedFileInfoEntity> riInserted = null;
		List<UploadedFileInfoEntity> riUpdated = null;
		List<UploadedFileInfoEntity> riDeleted = null;

		try {
			if (inserted != null) {
				riInserted = JsonHelper.jsonToBeanList(String.valueOf(inserted), UploadedFileInfoEntity.class);
				// 调用插入方法
				for (int index = 0; index < riInserted.size(); index++) {
					UploadedFileInfoEntity ri = riInserted.get(index);
					service.insertSelective(ri);
				}
			}
			if (updated != null) {
				riUpdated = JsonHelper.jsonToBeanList(String.valueOf(updated), UploadedFileInfoEntity.class);
				// 调用更新方法
				for (int index = 0; index < riUpdated.size(); index++) {
					UploadedFileInfoEntity ri = riUpdated.get(index);
					service.updateByPrimaryKey(ri);
				}
			}
			if (deleted != null) {
				riDeleted = JsonHelper.jsonToBeanList(String.valueOf(deleted), UploadedFileInfoEntity.class);
				// 调用删除方法
				for (int index = 0; index < riDeleted.size(); index++) {
					UploadedFileInfoEntity ri = riDeleted.get(index);
					service.deleteByPrimaryKey(ri.getId());
				}
			}
		} catch (Exception e) {
			return jsonResult(false, "错误信息:" + e.getMessage());
		}
		baseLogger.trace("saveData() <==");
		return jsonResult(true, "保存成功");
	}

	@RequestMapping(value = "deleteIndexFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIndexFile(HttpServletRequest request, HttpServletResponse response, String id) {
		baseLogger.trace("deleteIndexFile() ==>");
		SolrClientHelper sch = new SolrClientHelper();
		boolean bRet = sch.deleteSolrById(id);
		baseLogger.trace("deleteIndexFile() <==");
		if (bRet) {
			return jsonResult(true, "删除索引文件成功");
		}
		return jsonResult(false, "删除索引文件失败");
	}

	/*
	 * Convert comma seperated string to arraylist
	 */
	private ArrayList<String> convert2Collection(String str) {
		if (str != null && !"".equals(str)) {
			String[] valuesArray = str.split(",");
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			return values;
		}
		return null;
	}
	@RequestMapping(value = "updateIndexFile", method = RequestMethod.POST)
	@ResponseBody
//	public Map<String, Object> updateIndexFile(HttpServletRequest request, HttpServletResponse response, String id,
//			String keyword1, String keyword2, String keyword3, String keyword4, String keyword5,
//			String tag1, String tag2, String tag3, String tag4, String tag5, String tag6, String tag7, String tag8) {
	public Map<String, Object> updateIndexFile(HttpServletRequest request, HttpServletResponse response, String id,
			SysFileMeta sfm) {
		baseLogger.trace("updateIndexFile() ==>");
		SolrClientHelper sch = new SolrClientHelper();
		Map<String, Object> keywordsMap = null;
		if (sfm.getKeyword1() != null || sfm.getKeyword2() != null || sfm.getKeyword3() != null || sfm.getKeyword4() != null || sfm.getKeyword5() != null) {
			keywordsMap = new HashMap<String, Object>();
			String str = "";
			if (sfm.getKeyword1() != null && !"".equals(sfm.getKeyword1())) { str += sfm.getKeyword1(); str += ",";}
			if (sfm.getKeyword2() != null && !"".equals(sfm.getKeyword2())) { str += sfm.getKeyword2(); str += ",";}
			if (sfm.getKeyword3() != null && !"".equals(sfm.getKeyword3())) { str += sfm.getKeyword3(); str += ",";}
			if (sfm.getKeyword4() != null && !"".equals(sfm.getKeyword4())) { str += sfm.getKeyword4(); str += ",";}
			if (sfm.getKeyword5() != null && !"".equals(sfm.getKeyword5())) { str += sfm.getKeyword5(); str += ",";}
			keywordsMap.put("set", convert2Collection(str));
		}
//		if (keyword1 != null || keyword2 != null || keyword3 != null || keyword4 != null || keyword5 != null) {
//			keywordsMap = new HashMap<String, Object>();
//			if (keyword1 != null) keywordsMap.put("set", keyword1);
//			if (keyword2 != null) keywordsMap.put("set", keyword2);
//			if (keyword3 != null) keywordsMap.put("set", keyword3);
//			if (keyword4 != null) keywordsMap.put("set", keyword4);
//			if (keyword5 != null) keywordsMap.put("set", keyword5);
//		}
		Map<String, Object> tagsMap = null;
		if (sfm.getHzNumber1() != null || sfm.getHzNumber2() != null || sfm.getHzNumber3() != null || sfm.getHzNumber4() != null || sfm.getHzNumber5() != null || sfm.getHzNumber6() != null || sfm.getHzNumber7() != null || sfm.getHzNumber8() != null) {
			tagsMap = new HashMap<String, Object>();
			String str = "";
			if (sfm.getHzNumber1() != null && !"".equals(sfm.getHzNumber1())) { str += sfm.getHzNumber1(); str += ",";}
			if (sfm.getHzNumber2() != null && !"".equals(sfm.getHzNumber2())) { str += sfm.getHzNumber2(); str += ",";}
			if (sfm.getHzNumber3() != null && !"".equals(sfm.getHzNumber3())) { str += sfm.getHzNumber3(); str += ",";}
			if (sfm.getHzNumber4() != null && !"".equals(sfm.getHzNumber4())) { str += sfm.getHzNumber4(); str += ",";}
			if (sfm.getHzNumber5() != null && !"".equals(sfm.getHzNumber5())) { str += sfm.getHzNumber5(); str += ",";}
			if (sfm.getHzNumber6() != null && !"".equals(sfm.getHzNumber6())) { str += sfm.getHzNumber6(); str += ",";}
			if (sfm.getHzNumber7() != null && !"".equals(sfm.getHzNumber7())) { str += sfm.getHzNumber7(); str += ",";}
			if (sfm.getHzNumber8() != null && !"".equals(sfm.getHzNumber8())) { str += sfm.getHzNumber8(); str += ",";}
			tagsMap.put("set", convert2Collection(str));
		}

		boolean bRet = sch.updateSolrById(id, keywordsMap, tagsMap);
		baseLogger.trace("updateIndexFile() <==");
		if (bRet) {
			return jsonResult(true, "更新索引文件成功");
		}
		return jsonResult(false, "更新索引文件失败");
	}
}