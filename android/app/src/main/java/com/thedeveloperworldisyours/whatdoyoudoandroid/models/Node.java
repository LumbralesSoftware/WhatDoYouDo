package com.thedeveloperworldisyours.whatdoyoudoandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import library.annotations.Column;

/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class Node {

    public transient static final String COLUMN_MISSION = "mission";
    public transient static final String COLUMN_NODE_2 ="node_2";
    public transient static final String COLUMN_NODE_1 = "node_1";
    public transient static final String COLUMN_STATUS = "status";
    public transient static final String COLUMN_ANSWER_2 = "answer_2";
    public transient static final String COLUMN_ANSWER_1 = "answer_1";
    public transient static final String COLUMN_TEXT = "text";
    public transient static final String COLUMN_NAME = "name";
    public transient static final String COLUMN_ID = "_id";

    @Expose
    @Column(name = COLUMN_ID, isPrimaryKey = true)
    private double id;
    @Expose
    @Column(name = COLUMN_NAME)
    private String name;
    @Expose
    @Column(name = COLUMN_TEXT)
    private String text;
    @SerializedName("answer_1")
    @Expose
    @Column(name = COLUMN_ANSWER_1)
    private String answer1;
    @SerializedName("answer_2")
    @Expose
    @Column(name = COLUMN_ANSWER_2)
    private String answer2;
    @Expose
    @Column(name = COLUMN_STATUS)
    private Integer status;
    @SerializedName("node_1")
    @Expose
    @Column(name = COLUMN_NODE_1)
    private float node1;
    @SerializedName("node_2")
    @Expose
    @Column(name = COLUMN_NODE_2)
    private float node2;
    @Expose
    @Column(name = COLUMN_MISSION)
    private float mission;

    /**
     *
     * @return
     * The id
     */
    public double getId() {
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
     * The answer1
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     *
     * @param answer1
     * The answer_1
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     *
     * @return
     * The answer2
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     *
     * @param answer2
     * The answer_2
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The node1
     */
    public float getNode1() {
        return node1;
    }

    /**
     *
     * @param node1
     * The node_1
     */
    public void setNode1(float node1) {
        this.node1 = node1;
    }

    /**
     *
     * @return
     * The node2
     */
    public float getNode2() {
        return node2;
    }

    /**
     *
     * @param node2
     * The node_2
     */
    public void setNode2(float node2) {
        this.node2 = node2;
    }

    /**
     *
     * @return
     * The mission
     */
    public float getMission() {
        return mission;
    }

    /**
     *
     * @param mission
     * The mission
     */
    public void setMission(Integer mission) {
        this.mission = mission;
    }
}
