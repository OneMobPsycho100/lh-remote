package com.simple.shell.vo.req;

/**
 * ReqScriptExpandVO
 *
 * @author wuji
 * @date 2020-12-01 22:01
 */
public class ReqScriptExpandVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 扩展名称
     */
    private String name;

    /**
     * 扩展类型
     */
    private Integer type;

    /**
     * 扩展内容
     */
    private String content;

    /**
     * 描述
     */
    private String description;

    /**
     * 权重
     */
    private Integer weight;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
