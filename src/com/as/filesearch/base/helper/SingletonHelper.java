package com.as.filesearch.base.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.as.filesearch.entity.ObjType;
import com.as.filesearch.mapper.ObjTypeMapper;

public class SingletonHelper {

	private ApplicationContext beanFactory = null;
	private ObjTypeMapper mapper = null;

	private static SingletonHelper singleton = null;
	// 私有的构造函数，限制外部环境的非法创建和访问 
	private SingletonHelper() {
		//一些初始化操作 
		beanFactory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		//这个Main不能是自己new出来的，必须是从Spring容器中取出来的。
		mapper =  (ObjTypeMapper) beanFactory.getBean("objTypeMapper");
	}

	public static SingletonHelper getInstance() { 
	    if (singleton == null) { 
	      singleton = new SingletonHelper();
	    } 
	    return singleton;
	}
	/**
	 * 获取配置信息
	 * @return
	 */
	public String getTypeName(String typeType, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeType", typeType);
		param.put("type", type);

		List<ObjType> obj = mapper.selectBObjTypeByTypeType(param);
		String val = "";
		if (obj.size() > 0) {
			val = obj.get(0).getTypeName();
		}
		return val;
	}
}
