package com.as.filesearch.controller.portal.searchfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.ClientEntity;
import com.as.filesearch.entity.CourtEntity;
import com.as.filesearch.entity.JudgeEntity;
import com.as.filesearch.entity.LawFirmEntity;
import com.as.filesearch.entity.LawyerEntity;
import com.as.filesearch.entity.SysCaseCauseEntity;
import com.as.filesearch.service.admin.files.IClientEntityService;
import com.as.filesearch.service.admin.files.ICourtEntityService;
import com.as.filesearch.service.admin.files.IJudgeEntityService;
import com.as.filesearch.service.admin.files.ILawFirmEntityService;
import com.as.filesearch.service.admin.files.ILawyerEntityService;
import com.as.filesearch.service.admin.files.ISysCaseCauseEntityService;

@Controller
@RequestMapping(value = "/portal/searchfile/dictionary")
public class DictionaryController  extends PortalBaseController {

	@Resource(name = "SysCaseCauseEntityService")
	private ISysCaseCauseEntityService sysCaseCauseService;
	@Resource(name = "CourtEntityService")
	private ICourtEntityService courtEntityService;
	@Resource(name = "JudgeEntityService")
	private IJudgeEntityService judgeEntityService;
	@Resource(name = "ClientEntityService")
	private IClientEntityService clientEntityService;
	@Resource(name = "LawFirmEntityService")
	private ILawFirmEntityService lawFirmEntityService;
	@Resource(name = "LawyerEntityService")
	private ILawyerEntityService lawyerEntityService;

	@RequestMapping(value = "selectByPartName",  method = RequestMethod.POST )
	@ResponseBody
	public String[] selectByPartName(HttpServletRequest request, HttpServletResponse response) {
		PortalBaseController.baseLogger.trace("selectByPartName() ==>");
		List<String> items = new ArrayList<String>();
		try {
			String type = request.getParameter("type");
			String val = request.getParameter("val");
			PortalBaseController.baseLogger.trace("val=" + val);
			if (Constant._CAUSE_.equals(type)) {
				// Whether the caseCause maintained in db
				items = selectCauseByPartName(items, val);
			} else if (Constant._COURT_.equals(type)) {
				items = selectCourtByPartName(items, val);
			} else if (Constant._JUDGE_.equals(type)) {
				items = selectJudgeByPartName(items, val);
			} else if (Constant._LITIGANT_.equals(type)) {
				items = selectLitigantByPartName(items, val);
			} else if (Constant._EXECUTED_.equals(type)) {
				items = selectExecutedByPartName(items, val);
			} else if (Constant._EXECUTOR_.equals(type)) {
				items = selectExecutorByPartName(items, val);
			} else if (Constant._THIRDPART_.equals(type)) {
				items = selectThirdpartByPartName(items, val);
			} else if (Constant._LAWYER_.equals(type)) {
				items = selectLawyerByPartName(items, val);
			} else if (Constant._LAWFIRM_.equals(type)) {
				items = selectLawfirmByPartName(items, val);
			} else if (Constant._LEGALPERSON_.equals(type)) {
				items = selectLegalPersonByPartName(items, val);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			PortalBaseController.baseLogger.error("selectByPartName() <==. Cause:" + e.getMessage());
		}

		PortalBaseController.baseLogger.trace("selectByPartName() <==, returns {true," + items.toString() + "}");
		return (String[])items.toArray(new String[items.size()]);
	}
	
	private List<String> selectCauseByPartName(List<String> items, String val) {
		List<SysCaseCauseEntity> entityList = sysCaseCauseService.selectByPartName(val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}

	private List<String> selectCourtByPartName(List<String> items, String val) {
		List<CourtEntity> entityList = courtEntityService.selectByPartName(val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getFullName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}

	private List<String> selectJudgeByPartName(List<String> items, String val) {
		List<JudgeEntity> entityList = judgeEntityService.selectByPartName("0", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}

	private List<String> selectLitigantByPartName(List<String> items, String val) {
		List<ClientEntity> entityList = clientEntityService.selectByPartName("A", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectExecutorByPartName(List<String> items, String val) {
		List<ClientEntity> entityList = clientEntityService.selectByPartName("0", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectExecutedByPartName(List<String> items, String val) {
		List<ClientEntity> entityList = clientEntityService.selectByPartName("1", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectThirdpartByPartName(List<String> items, String val) {
		List<ClientEntity> entityList = clientEntityService.selectByPartName("2", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectLegalPersonByPartName(List<String> items, String val) {
		List<ClientEntity> entityList = clientEntityService.selectByPartName("3", val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectLawyerByPartName(List<String> items, String val) {
		List<LawyerEntity> entityList = lawyerEntityService.selectByPartName(val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
	private List<String> selectLawfirmByPartName(List<String> items, String val) {
		List<LawFirmEntity> entityList = lawFirmEntityService.selectByPartName(val);
		for (int i = 0; i < entityList.size(); i ++) {
			String fullName = entityList.get(i).getName();
			Integer id = entityList.get(i).getId();
			items.add(id + "_" + fullName);
		}
		return items;
	}
}