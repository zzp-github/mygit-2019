package com.shop.solr.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Items implements Serializable{
    private Integer id;

    @Field("item_title")
    private String name;
    @Field("item_price")
    private Integer price;
    
    private String pic;

    private Date createtime;
    @Field("item_desc")
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
    
    public String getFormatCreateTime() {
    	return new SimpleDateFormat("yyyy-MM-dd").format(this.createtime);
    }
}