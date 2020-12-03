package com.simple.shell.vo.req;

/**
 * ReqRecordSearchVO
 *
 * @author wuji
 * @date 2020-12-02 22:04
 */
public class ReqRecordSearchVO {

    private Long page;

    private Long size;

    private String scriptName;

    private Long startTime;

    private Long endTime;


    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
