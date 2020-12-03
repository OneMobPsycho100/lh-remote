package com.simple.shell.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.shell.common.BaseEntity;

/**
 * ScriptExpandEntity
 *
 * @author wuji
 * @date 2020-11-30 23:19
 */
@TableName("script_expand")
public class ScriptExpandEntity extends BaseEntity {

    /**
     * 关联脚本 ID
     */
    @TableField("script_id")
    private Integer scriptId;

    /**
     * 扩展名称
     */
    @TableField("name")
    private String name;

    /**
     * 扩展类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 扩展内容
     */
    @TableField("content")
    private String content;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 参数权重
     */
    @TableField("weight")
    private Integer weight;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
