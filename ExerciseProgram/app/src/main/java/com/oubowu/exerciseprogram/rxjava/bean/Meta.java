
package com.oubowu.exerciseprogram.rxjava.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Meta {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("documentId")
    @Expose
    private String documentId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expiredTime")
    @Expose
    private Integer expiredTime;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;

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
     *     The expiredTime
     */
    public Integer getExpiredTime() {
        return expiredTime;
    }

    /**
     * 
     * @param expiredTime
     *     The expiredTime
     */
    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * 
     * @return
     *     The pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     *     The pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
