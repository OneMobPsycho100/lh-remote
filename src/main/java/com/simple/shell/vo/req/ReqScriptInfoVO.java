package com.simple.shell.vo.req;

import java.util.List;

/**
 * ReqScriptInfoVO
 *
 * @author wuji
 * @date 2020-12-01 21:59
 */
public class ReqScriptInfoVO {

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

    private List<ReqScriptExpandVO> scriptExpandVOList;


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

    public List<ReqScriptExpandVO> getScriptExpandVOList() {
        return scriptExpandVOList;
    }

    public void setScriptExpandVOList(List<ReqScriptExpandVO> scriptExpandVOList) {
        this.scriptExpandVOList = scriptExpandVOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
