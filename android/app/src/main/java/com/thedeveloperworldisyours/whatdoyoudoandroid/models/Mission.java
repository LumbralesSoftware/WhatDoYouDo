package com.thedeveloperworldisyours.whatdoyoudoandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class Mission {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String text;
    @Expose
    private Integer beginning;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The beginning
     */
    public Integer getBeginning() {
        return beginning;
    }

    /**
     *
     * @param beginning
     * The beginning
     */
    public void setBeginning(Integer beginning) {
        this.beginning = beginning;
    }

}
