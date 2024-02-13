var formatterOrgType = function(value, row, index) {
	if (value) {
		if (value == 3) {
			return "养老机构";
		} else if (value == 2) {
			return "医疗机构";
		} else if (value == 1) {
			return "政府机构";
		}
	}
	return "";
};

var formatterWhether = function(value, row, index) {
	if (value) {
		if (value == 1) {
			return "是";
		} else if (value == 0) {
			return "否";
		}
	}
	return "否";
};

var formatterFileType = function(value, row, index) {
	if (value) {
		if (value == 1) {
			return "更新包";
		} else if (value == 2) {
			return "完整包";
		}
	}
	return "";
};

var formatterIsSuccess = function(value, row, index) {
	if (value) {
		if (value == 'Y') {
			return "成功";
		} else if (value == 'N') {
			return "失败";
		}
	}
	return "失败";
};

/**
 * 根据字典类别编码和字典项key获取字典项value值
 * 
 * @param categoryCode
 *            字典类别编码
 * @param dictionaryKey
 *            字典项key
 */
var formatterDictionary = function(categoryCode, dictionaryKey) {
	if (categoryCode && dictionaryKey) {
		for ( var code in dictionary) {
			if (dictionary[code].code == categoryCode) {
				var dictionaryItems = dictionary[code].dictionaryItems;
				for ( var item in dictionaryItems) {
					if (dictionaryItems[item].key == dictionaryKey) {
						return dictionaryItems[item].value;
					}
				}
			}
		}
	}
	return "";
};

/**
 * 根据省市县编码获取省市县名称
 * 
 * @param value
 *            省市县编码
 */
var formatterRegionName = function(value, row, index) {
	if (value) {
		return getRegionName(value);
	}
	return "";
};

var formatterFileUploadStatus = function(value, row, index) {
	if (value) {
		if (value == 1) {
			return "已上传";
		} else if (value == 2) {
			return "<font color='green'>导入成功</font>";
		} else if (value == 3) {
			return "<font color='orange'>导入失败</font>";
		}
	}
	return "";
};

// 用户管理----性别
var formatterSex = function(value, row, index) {
	if (value == 0) {
		return "女";
	} else if (value == 1) {
		return "男";
	} else {
		return "男";
	}
};

// 用户管理---是否管理员
var formatterUserType = function(value, row, index) {

	if (value == 1) {
		return "管理员";
	} else if (value == 0) {
		return "普通用户";
	} else {
		return "普通用户";
	}
};

// 用户管理--是否禁用
var formatterIsDisable = function(value, row, index) {

	if (value == 1) {
		return "是";
	} else if (value == 0) {
		return "否";
	} else {
		return "否";
	}
};

// 新闻栏目管理----栏目状态
var formatterNewsColumnStatus = function(value, row, index) {
	if (value == 0) {
		return "启用";
	} else if (value == 1) {
		return "禁用";
	} else {
		return "禁用";
	}
};
