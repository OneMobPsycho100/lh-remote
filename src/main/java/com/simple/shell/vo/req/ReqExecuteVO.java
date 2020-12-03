package com.simple.shell.vo.req;

import java.util.List;

/**
 * ReqExecuteVO
 *
 * @author wuji
 * @date 2020-12-02 21:19
 */
public class ReqExecuteVO {

    private Integer id;

    private List<Integer> expandIdList;

    private String extraInfo;

    private String desc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getExpandIdList() {
        return expandIdList;
    }

    public void setExpandIdList(List<Integer> expandIdList) {
        this.expandIdList = expandIdList;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
