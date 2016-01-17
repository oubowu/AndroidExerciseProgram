
package com.oubowu.exerciseprogram.rxjava.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Item {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("createtime")
    @Expose
    private String createtime;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("commentsall")
    @Expose
    private Integer commentsall;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("documentId")
    @Expose
    private String documentId;
    @SerializedName("links")
    @Expose
    private List<Link> links = new ArrayList<Link>();

    /**
     * 
     * @return
     *     The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 
     * @param introduction
     *     The introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 
     * @return
     *     The updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime
     *     The updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return
     *     The createtime
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * 
     * @param createtime
     *     The createtime
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * 
     * @return
     *     The commentsall
     */
    public Integer getCommentsall() {
        return commentsall;
    }

    /**
     * 
     * @param commentsall
     *     The commentsall
     */
    public void setCommentsall(Integer commentsall) {
        this.commentsall = commentsall;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * 
     * @param documentId
     *     The documentId
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
