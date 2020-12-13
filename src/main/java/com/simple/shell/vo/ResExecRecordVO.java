package com.simple.shell.vo;

import java.time.LocalDateTime;

/**
 * ResExecRecordVO
 *
 * @author wuji
 * @date 2020-12-02 21:57
 */
public class ResExecRecordVO {

    private Integer id;

    /**
     * 关联脚本 ID
     */
    private Integer scriptId;

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 执行记录
     */
    private String name;


    /**
     * 可能是异常信息或者其它
     */
    private String content;

    /**
     * 9执行失败，0执行中，1执行成功
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
}
