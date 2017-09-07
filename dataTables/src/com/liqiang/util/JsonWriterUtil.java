package com.liqiang.util;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

public class JsonWriterUtil {
	
	/**
	 * 流方式刷出数据
	 * @param response
	 */
	public static void strJsonWriter(HttpServletResponse response,Object obj){
		try {
			response.setContentType("text/javascript;charset=utf-8");
			PrintWriter out = response.getWriter();
			JSONObject json=JSONObject.fromObject(obj);
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 流方式刷出数据 含过滤条件
	 * @author  李强
	 * @param obj 
	 *         序列化的数据对象
	 * @param filterPropertyNames
	 *         需要过滤掉的字段名
	 * @param  @param
	 *          HttpServletResponse
	 * @Time 2017-09-01
	 */
	public static void strJsonWriter(HttpServletResponse response,Object obj,final List<String> filterPropertyNames){
		try {
			response.setContentType("text/javascript;charset=utf-8");
			PrintWriter out = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			//作用排除hibernate延迟加载是代理类对象
			//jsonConfig.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
			//排除实体类字包含的字段
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			if(filterPropertyNames!=null) {
				//过滤掉不需要的字段
				jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
					public boolean apply(Object source, String name, Object value) {
						if (filterPropertyNames.contains(name)) {
							return true;
						} else {
							return false;
						}

					}
				});	
			}
			
			out.println(JSONObject.fromObject(obj, jsonConfig).toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
