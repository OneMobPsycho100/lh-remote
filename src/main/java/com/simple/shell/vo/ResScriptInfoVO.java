package com.simple.shell.vo;

import java.util.List;

/**
 * ResScriptInfoVO
 *
 * @author wuji
 * @date 2020-12-01 22:20
 */
public class ResScriptInfoVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 脚本名称
     */
    private String name;

    /**
     * 脚本执行内容
     */
    private String content;

    /**
     * 0 不生成文件，1生成文件
     */
    private Integer generateFile;

    /**
     * 脚本文件生成目录
     */
    private String directory;

    /**
     * 脚本描述信息
     */
    private String description;

    /**
     * 拓展信息集合
     */
    private List<ResScriptExpandVO> resScriptExpandVOList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGenerateFile() {
        return generateFile;
    }

    public void setGenerateFile(Integer generateFile) {
        this.generateFile = generateFile;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResScriptExpandVO> getResScriptExpandVOList() {
        return resScriptExpandVOList;
    }

    public void setResScriptExpandVOList(List<ResScriptExpandVO> resScriptExpandVOList) {
        this.resScriptExpandVOList = resScriptExpandVOList;
    }
}
