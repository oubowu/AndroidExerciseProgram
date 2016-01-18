
package com.oubowu.exerciseprogram.rxjava.model.ifeng;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Body {

    @SerializedName("item")
    @Expose
    public List<Item> item = new ArrayList<Item>();

}
