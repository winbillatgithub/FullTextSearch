package com.as.filesearch.base.mapper;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * 时间类型转换，处理json返回时间类型的格式
 * @author 熊焱
 * @date 2013-8-29 上午09:45:33  
 */
public class DateObjectMapper extends ObjectMapper implements InitializingBean, BeanNameAware {

	private static final long serialVersionUID = 1L;

	private String beanName;
	private String pattern = "yyyy-MM-dd HH:mm:ss";

	public void afterPropertiesSet() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(this.pattern);
		this.setDateFormat(df);
	}
	
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getBeanName() {
		return beanName;
	}
}
