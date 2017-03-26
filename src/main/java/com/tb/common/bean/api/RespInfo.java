package com.tb.common.bean.api;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @author acer11
 *  作者：haoxud
 *   创建时间：2017年3月26日 下午8:23:27  
* 项目名称：tb-common  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：RespInfo.java  
* 类说明：返回接口数据
 */
public class RespInfo<T>  implements Serializable{

    public RespInfo()
    {
    }

    public String getRespCode()
    {
        return respCode;
    }

    public void setRespCode(String respCode)
    {
        this.respCode = respCode;
    }

    public String getRespDesc()
    {
        return respDesc;
    }

    public void setRespDesc(String respDesc)
    {
        this.respDesc = respDesc;
    }

    public Object getData()
    {
        return data;
    }

    public Object getData(Class clazz)
    {
        if(data != null && !clazz.equals(data.getClass()))
            return JSON.parseObject(data.toString(), clazz);
        else
            return data;
    }
   

    public void setData(Object data)
    {
        this.data = data;
    }

    public RespInfo(String respCode, String respDesc, String time, Object data) {
	
		this.respCode = respCode;
		this.respDesc = respDesc;
		this.time = time;
		this.data = data;
	}

	public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    private static final long serialVersionUID = 1L;
    private String respCode;
    private String respDesc;
    private String time;
    private Object data;
}
