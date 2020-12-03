package com.simple.shell.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.shell.common.BaseEntity;

/**
 * ExecRecord
 *
 * @author wuji
 * @date 2020-11-30 23:19
 */
@TableName("exec_record")
public class ExecRecordEntity extends BaseEntity {

    /**
     * 关联脚本 ID
     */
    @TableField("script_id")
    private Integer scriptId;

    /**
     * 脚本名称 冗余
     */
    @TableField("script_name")
    private String scriptName;

    /**
     * 执行记录
     */
    @TableField("name")
    private String name;

    /**
     * 可能是异常信息或者其它
     */
    @TableField("content")
    private String content;

    /**
     * 9执行失败，0执行中，1执行成功
     */
    @TableField("status")
    private Integer status;

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
}
