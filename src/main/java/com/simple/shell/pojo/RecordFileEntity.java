package com.simple.shell.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.shell.common.BaseEntity;

/**
 * RecordFileEntity
 *
 * @author wuji
 * @date 2020-11-30 23:20
 */
@TableName("record_file")
public class RecordFileEntity extends BaseEntity {

    /**
     * 关联执行记录ID
     */
    @TableField("record_id")
    private Integer recordId;

    /**
     * 文件名
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 文件地址
     */
    @TableField("address")
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
}
