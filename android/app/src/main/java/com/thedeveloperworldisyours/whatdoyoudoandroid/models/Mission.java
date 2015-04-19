package com.thedeveloperworldisyours.whatdoyoudoandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import library.annotations.Column;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
@SuppressWarnings("UnusedDeclaration")
public class Mission {

    public transient static final String COLUMN_BEGINNING = "beginning";
    public transient static final String COLUMN_TEXT = "text";
    public transient static final String COLUMN_NAME = "name";
    public transient static final String COLUMN_ID = "_id";

    @Expose
    @Column(name = COLUMN_ID, isPrimaryKey = true)
    private String id;
    @Expose
    @Column(name = COLUMN_NAME)
    private String name;
    @Expose
    @Column(name = COLUMN_TEXT)
    private String text;
    @Expose
    @Column(name = COLUMN_BEGINNING)
    private String beginning;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
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
    public String getBeginning() {
        return beginning;
    }

    /**
     *
     * @param beginning
     * The beginning
     */
    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

}
