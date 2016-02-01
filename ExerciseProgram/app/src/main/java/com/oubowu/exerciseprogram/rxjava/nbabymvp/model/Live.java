
package com.oubowu.exerciseprogram.rxjava.nbabymvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("org.jsonschema2pojo")
public class Live {

    @SerializedName("player1")
    @Expose
    public String player1;
    @SerializedName("player1info")
    @Expose
    public String player1info;
    @SerializedName("player1location")
    @Expose
    public String player1location;
    @SerializedName("player1logobig")
    @Expose
    public String player1logobig;
    @SerializedName("player1url")
    @Expose
    public String player1url;
    @SerializedName("player2")
    @Expose
    public String player2;
    @SerializedName("player2info")
    @Expose
    public String player2info;
    @SerializedName("player2location")
    @Expose
    public String player2location;
    @SerializedName("player2logobig")
    @Expose
    public String player2logobig;
    @SerializedName("player2url")
    @Expose
    public String player2url;
    @SerializedName("score")
    @Expose
    public String score;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("liveurl")
    @Expose
    public String liveurl;

}
