
package com.oubowu.exerciseprogram.rxjava.nbabymvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("org.jsonschema2pojo")
public class Bottomlink {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("url")
    @Expose
    public String url;

}
