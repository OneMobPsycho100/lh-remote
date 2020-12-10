package com.simple.shell.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.shell.common.BaseEntity;

/**
 * ScriptEntity
 *
 * @author wuji
 * @date 2020-11-30 23:19
 */
@TableName("script")
public class ScriptEntity extends BaseEntity {
    /**
     * 脚本名称
     */
    @TableField("name")
    private String name;

    /**
     * 脚本执行内容
     */
    @TableField("content")
    private String content;

//    /**
//     * 0 不生成文件，1生成文件
//     */
//    private Integer generateFile;

    /**
     * 脚本描述信息
     */
    @TableField("description")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Integer getGenerateFile() {
//        return generateFile;
//    }
//
//    public void setGenerateFile(Integer generateFile) {
//        this.generateFile = generateFile;
//    }
}
