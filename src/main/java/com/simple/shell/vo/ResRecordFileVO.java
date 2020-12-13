package com.simple.shell.vo;

/**
 * ResRecordFileVO
 *
 * @author wuji
 * @date 2020-12-02 22:47
 */
public class ResRecordFileVO {

    private Integer id;

    /**
     * 关联执行记录ID
     */
    private Integer recordId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 文件地址
     */
    private String address;


    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

