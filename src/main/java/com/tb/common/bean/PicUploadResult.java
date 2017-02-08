package com.tb.common.bean;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年2月7日 上午10:43:11  
* 项目名称：tb-common  
* 文件名称：PicUploadResult.java  
* 类说明：
 */
public class PicUploadResult {
    
    private Integer error;
    
    private String url;
    
    private String width;
    
    private String height;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    
    

}
