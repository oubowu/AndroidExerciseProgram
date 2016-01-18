
package com.oubowu.exerciseprogram.rxjava.model.ifeng;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Item {

    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("introduction")
    @Expose
    public String introduction;
    @SerializedName("updateTime")
    @Expose
    public String updateTime;
    @SerializedName("createtime")
    @Expose
    public String createtime;
    @SerializedName("comments")
    @Expose
    public int comments;
    @SerializedName("commentsall")
    @Expose
    public int commentsall;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("documentId")
    @Expose
    public String documentId;
    @SerializedName("links")
    @Expose
    public List<Link> links = new ArrayList<Link>();

}
