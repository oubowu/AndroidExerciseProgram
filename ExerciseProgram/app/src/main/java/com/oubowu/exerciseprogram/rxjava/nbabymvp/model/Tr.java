
package com.oubowu.exerciseprogram.rxjava.nbabymvp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/*
因为JacksonConverterFactory默认要求必须存在相应的字段。如果没有前台传来的某个字段，就会报错
这些方法主要是使用Jackson提供的json注解。
@JsonIgnore注解用来忽略某些字段，可以用在Field或者Getter方法上，用在Setter方法时，和Filed效果一样。这个注解只能用在POJO存在的字段要忽略的情况，不能满足现在需要的情况。
@JsonIgnoreProperties(ignoreUnknown = true)，将这个注解写在类上之后，就会忽略类中不存在的字段，可以满足当前的需要。这个注解还可以指定要忽略的字段。使用方法如下：
@JsonIgnoreProperties({ "internalId", "secretKey" })
指定的字段不会被序列化和反序列化。
*/

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("org.jsonschema2pojo")
public class Tr {

    @SerializedName("link1text")
    @Expose
    public String link1text;
    @SerializedName("link1url")
    @Expose
    public String link1url;
    @SerializedName("link2text")
    @Expose
    public String link2text;
    @SerializedName("link2url")
    @Expose
    public String link2url;
    @SerializedName("m_link1url")
    @Expose
    public String mLink1url;
    @SerializedName("m_link2url")
    @Expose
    public String mLink2url;
    @SerializedName("player1")
    @Expose
    public String player1;
    @SerializedName("player1logo")
    @Expose
    public String player1logo;
    @SerializedName("player1logobig")
    @Expose
    public String player1logobig;
    @SerializedName("player1url")
    @Expose
    public String player1url;
    @SerializedName("player2")
    @Expose
    public String player2;
    @SerializedName("player2logo")
    @Expose
    public String player2logo;
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
    @SerializedName("time")
    @Expose
    public String time;

}
