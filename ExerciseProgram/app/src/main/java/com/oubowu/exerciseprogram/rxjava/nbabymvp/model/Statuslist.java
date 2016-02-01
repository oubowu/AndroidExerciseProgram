
package com.oubowu.exerciseprogram.rxjava.nbabymvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("org.jsonschema2pojo")
public class Statuslist {

    @SerializedName("st0")
    @Expose
    public String st0;
    @SerializedName("st1")
    @Expose
    public String st1;
    @SerializedName("st2")
    @Expose
    public String st2;

}
