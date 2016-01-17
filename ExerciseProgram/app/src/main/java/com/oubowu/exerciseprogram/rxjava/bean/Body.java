
package com.oubowu.exerciseprogram.rxjava.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Body {

    @SerializedName("item")
    @Expose
    private List<Item> item = new ArrayList<Item>();

    /**
     * 
     * @return
     *     The item
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    public void setItem(List<Item> item) {
        this.item = item;
    }

}
