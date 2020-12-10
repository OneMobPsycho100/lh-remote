package com.simple.shell.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.shell.utils.Utils;
import com.simple.shell.dao.ExecRecordRepository;
import com.simple.shell.pojo.ExecRecordEntity;
import com.simple.shell.vo.ResExecRecordVO;
import com.simple.shell.vo.req.ReqRecordSearchVO;
import com.simple.shell.service.IExecRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ExecRecordServiceImpl
 *
 * @author wuji
 * @date 2020-11-30 23:24
 */
@Service
public class ExecRecordServiceImpl extends ServiceImpl<ExecRecordRepository, ExecRecordEntity> implements IExecRecordService {


    @Override
    public IPage<ResExecRecordVO> pageExecRecordById(ReqRecordSearchVO recordSearchVO) {
        Page<ExecRecordEntity> entityPage = this.lambdaQuery()
                .like(!StringUtils.isEmpty(recordSearchVO.getScriptName()),
                        ExecRecordEntity::getScriptName, recordSearchVO.getScriptName())
                .ge(recordSearchVO.getStartTime() != null,
                        ExecRecordEntity::getCreateTime, Utils.ofMilliseconds(recordSearchVO.getStartTime()))

                .le(recordSearchVO.getEndTime() != null, ExecRecordEntity::getCreateTime, Utils.ofMilliseconds(recordSearchVO.getEndTime()))
                .page(new Page<>(recordSearchVO.getPage(), recordSearchVO.getSize()));

        List<ResExecRecordVO> execRecordVOList = entityPage.getRecords()
                .stream()
                .map(it -> {
                    ResExecRecordVO execRecordVO = new ResExecRecordVO();
                    BeanUtils.copyProperties(it, execRecordVO);
                    return execRecordVO;
                })
                .collect(Collectors.toList());
        Page<ResExecRecordVO> resultPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        resultPage.setRecords(execRecordVOList);
        resultPage.setTotal(entityPage.getTotal());
        resultPage.setPages(entityPage.getPages());
        return resultPage;
    }

    @Override
    public ResExecRecordVO info(Integer id) {
        ExecRecordEntity recordEntity = getById(id);
        Assert.notNull(recordEntity, "数据不存在或已删除");
        ResExecRecordVO resExecRecordVO = new ResExecRecordVO();
        BeanUtils.copyProperties(recordEntity, resExecRecordVO);
        return resExecRecordVO;
    }


}
