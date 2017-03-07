package com.tb.common.bean.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年2月27日 下午10:44:22  
* 项目名称：tb-common  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemCatData.java  
* 类说明：商品类目 为返回商品json
 */
public class ItemCatData  {
	
	@JsonProperty("u") // 序列化成json数据时为 u
	private String url;
	
	@JsonProperty("n")
	private String name;
	
	@JsonProperty("i")
	private List<?> items;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}
	
	

}
