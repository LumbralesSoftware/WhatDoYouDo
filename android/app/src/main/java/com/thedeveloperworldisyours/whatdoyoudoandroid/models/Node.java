package com.thedeveloperworldisyours.whatdoyoudoandroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by javiergonzalezcabezas on 18/4/15.
 */
public class Node {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String text;
    @SerializedName("answer_1")
    @Expose
    private String answer1;
    @SerializedName("answer_2")
    @Expose
    private String answer2;
    @Expose
    private Integer status;
    @SerializedName("node_1")
    @Expose
    private Object node1;
    @SerializedName("node_2")
    @Expose
    private Object node2;
    @Expose
    private Integer mission;

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
    public Object getNode1() {
        return node1;
    }

    /**
     *
     * @param node1
     * The node_1
     */
    public void setNode1(Object node1) {
        this.node1 = node1;
    }

    /**
     *
     * @return
     * The node2
     */
    public Object getNode2() {
        return node2;
    }

    /**
     *
     * @param node2
     * The node_2
     */
    public void setNode2(Object node2) {
        this.node2 = node2;
    }

    /**
     *
     * @return
     * The mission
     */
    public Integer getMission() {
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
