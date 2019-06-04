package top.luyuni.qaa.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;


public class Feed {
    private int id;
    /**
     * 新鲜事type   如点赞、评论  因为渲染不一样记录一下type
     */
    private int type;
    /**
     * 由谁产生的新鲜事
     */
    private int userId;
    private Date createdDate;

    private String data;
    private JSONObject dataJSON = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }
    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }
}
