
package com.oubowu.exerciseprogram.rxjava.nbademo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class NBA {

    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("error_code")
    @Expose
    public int errorCode;

}
