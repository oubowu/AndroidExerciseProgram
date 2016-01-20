
package com.oubowu.exerciseprogram.rxjava.nbademo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Result {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("statuslist")
    @Expose
    public Statuslist statuslist;
    @SerializedName("list")
    @Expose
    public java.util.List<MatchList> list = new ArrayList<MatchList>();
    @SerializedName("teammatch")
    @Expose
    public java.util.List<Teammatch> teammatch = new ArrayList<Teammatch>();

}
