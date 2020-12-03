package com.simple.shell.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.shell.pojo.ExecRecordEntity;
import com.simple.shell.vo.ResExecRecordVO;
import com.simple.shell.vo.req.ReqRecordSearchVO;

/**
 * IExecRecordService
 *
 * @author wuji
 * @date 2020-11-30 23:21
 */
public interface IExecRecordService extends IService<ExecRecordEntity> {

    /**
     * 分页查询执行记录
     *
     * @param recordSearchVO recordSearchVO
     * @return IPage<ResExecRecordVO>
     */
    IPage<ResExecRecordVO> pageExecRecordById(ReqRecordSearchVO recordSearchVO);

    /**
     * 执行记录详情
     *
     * @param id id
     * @return ResExecRecordVO
     */
    ResExecRecordVO info(Integer id);
}