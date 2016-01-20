
package com.oubowu.exerciseprogram.rxjava.nbabymvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MatchList {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("tr")
    @Expose
    public java.util.List<Tr> tr = new ArrayList<Tr>();
    @SerializedName("bottomlink")
    @Expose
    public java.util.List<Bottomlink> bottomlink = new ArrayList<Bottomlink>();
    @SerializedName("live")
    @Expose
    public java.util.List<Live> live = new ArrayList<Live>();
    @SerializedName("livelink")
    @Expose
    public java.util.List<Livelink> livelink = new ArrayList<Livelink>();

}
