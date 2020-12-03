package com.simple.shell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.shell.pojo.ScriptEntity;
import com.simple.shell.vo.ResScriptInfoVO;
import com.simple.shell.vo.req.ReqExecuteVO;
import com.simple.shell.vo.req.ReqScriptInfoVO;

/**
 * IScriptService
 *
 * @author wuji
 * @date 2020-11-30 23:22
 */
public interface IScriptService extends IService<ScriptEntity> {

    /**
     * 添加脚本信息
     *
     * @param scriptInfoVO scriptInfoVO
     */
    void saveScriptInfo(ReqScriptInfoVO scriptInfoVO);

    /**
     * 查询脚本列表
     *
     * @param page page
     * @return
     */
    IPage<ResScriptInfoVO> pageScript(Page<ScriptEntity> page);

    /**
     * 脚本信息详情
     *
     * @param id 脚本ID
     * @return
     */
    ResScriptInfoVO scriptInfo(Integer id);

    /**
     * 编辑脚本信息
     *
     * @param reqScriptInfoVO reqScriptInfoVO
     */
    void updateScriptInfo(ReqScriptInfoVO reqScriptInfoVO);

    /**
     * 执行脚本
     *
     * @param reqExecuteVO reqExecuteVO
     */
    void execute(ReqExecuteVO reqExecuteVO) throws Exception;
}