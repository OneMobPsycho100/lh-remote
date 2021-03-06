package com.simple.shell.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.shell.config.RemoteCallback;
import com.simple.shell.config.RemoteShellExecutor;
import com.simple.shell.dao.ScriptRepository;
import com.simple.shell.pojo.ExecRecordEntity;
import com.simple.shell.pojo.ScriptEntity;
import com.simple.shell.pojo.ScriptExpandEntity;
import com.simple.shell.service.IExecRecordService;
import com.simple.shell.service.IScriptExpandService;
import com.simple.shell.service.IScriptService;
import com.simple.shell.vo.ResScriptExpandVO;
import com.simple.shell.vo.ResScriptInfoVO;
import com.simple.shell.vo.req.ReqExecuteVO;
import com.simple.shell.vo.req.ReqScriptExpandVO;
import com.simple.shell.vo.req.ReqScriptInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScriptServiceImpl
 *
 * @author wuji
 * @date 2020-11-30 23:25
 */
@Service
public class ScriptServiceImpl extends ServiceImpl<ScriptRepository, ScriptEntity> implements IScriptService {


    private final IScriptExpandService scriptExpandService;

    private final RemoteShellExecutor remoteShellExecutor;

    private final IExecRecordService execRecordService;

    @Autowired
    public ScriptServiceImpl(IScriptExpandService scriptExpandService,
                             RemoteShellExecutor remoteShellExecutor,
                             IExecRecordService execRecordService) {
        this.scriptExpandService = scriptExpandService;
        this.remoteShellExecutor = remoteShellExecutor;
        this.execRecordService = execRecordService;
    }

    @Override
    public void saveScriptInfo(ReqScriptInfoVO scriptInfoVO) {
        ScriptEntity scriptEntity = new ScriptEntity();
        BeanUtils.copyProperties(scriptInfoVO, scriptEntity);
        this.save(scriptEntity.buildAddEntity());

        Integer scriptId = scriptEntity.getId();
        List<ReqScriptExpandVO> scriptExpandVOList = scriptInfoVO.getScriptExpandVOList();
        if (!CollectionUtils.isEmpty(scriptExpandVOList)) {
            saveBatchExpand(scriptId, scriptExpandVOList);
        }
    }

    private void saveBatchExpand(Integer scriptId, List<ReqScriptExpandVO> scriptExpandVOList) {
        List<ScriptExpandEntity> scriptExpandEntityList = scriptExpandVOList.stream()
                .map(it -> {
                    ScriptExpandEntity scriptExpandEntity = new ScriptExpandEntity();
                    BeanUtils.copyProperties(it, scriptExpandEntity);
                    scriptExpandEntity.setScriptId(scriptId);
                    scriptExpandEntity.buildAddEntity();
                    return scriptExpandEntity;
                })
                .collect(Collectors.toList());
        scriptExpandService.saveBatch(scriptExpandEntityList);
    }

    @Override
    public IPage<ResScriptInfoVO> pageScript(Page<ScriptEntity> page) {
        Page<ScriptEntity> entityPage = lambdaQuery()
                .orderByDesc(ScriptEntity::getCreateTime).page(page);
        List<ResScriptInfoVO> resScriptInfoVOS = entityPage.getRecords().stream().map(it -> {
            Integer id = it.getId();
            List<ResScriptExpandVO> scriptExpandVOList = scriptExpandService.listExpandByScriptId(id);
            ResScriptInfoVO resScriptInfoVO = new ResScriptInfoVO();
            BeanUtils.copyProperties(it, resScriptInfoVO);
            resScriptInfoVO.setResScriptExpandVOList(scriptExpandVOList
                    .stream().filter(vo -> vo.getType() == 1).collect(Collectors.toList()));
            return resScriptInfoVO;
        }).collect(Collectors.toList());

        Page<ResScriptInfoVO> resultPage = new Page<>(entityPage.getCurrent(), entityPage.getSize());
        resultPage.setRecords(resScriptInfoVOS);
        resultPage.setTotal(entityPage.getTotal());
        resultPage.setPages(entityPage.getPages());

        return resultPage;
    }

    @Override
    public ResScriptInfoVO scriptInfo(Integer id) {
        ScriptEntity scriptEntity = this.getById(id);
        Assert.notNull(scriptEntity, "数据不存在或已删除");

        ResScriptInfoVO resScriptInfoVO = new ResScriptInfoVO();
        BeanUtils.copyProperties(scriptEntity, resScriptInfoVO);

        List<ResScriptExpandVO> scriptExpandList = scriptExpandService.listExpandByScriptId(id);
        List<ResScriptExpandVO> reqScriptExpandVOList = scriptExpandList.stream().map(it -> {
            ResScriptExpandVO reqScriptExpandVO = new ResScriptExpandVO();
            BeanUtils.copyProperties(it, reqScriptExpandVO);
            return reqScriptExpandVO;
        }).collect(Collectors.toList());
        resScriptInfoVO.setResScriptExpandVOList(reqScriptExpandVOList);
        return resScriptInfoVO;
    }

    @Override
    public void updateScriptInfo(ReqScriptInfoVO reqScriptInfoVO) {
        Assert.notNull(getById(reqScriptInfoVO.getId()), "数据不存在或已删除");
        ScriptEntity scriptEntity = new ScriptEntity();
        BeanUtils.copyProperties(reqScriptInfoVO, scriptEntity);
        this.updateById(scriptEntity.buildUpdateEntity());
        List<ReqScriptExpandVO> scriptExpandVOList = reqScriptInfoVO.getScriptExpandVOList();
        if (CollectionUtils.isEmpty(scriptExpandVOList)) {
            return;
        }
        saveBatchExpand(scriptEntity.getId(), scriptExpandVOList);
    }

    @Override
    public void execute(ReqExecuteVO reqExecuteVO) throws Exception {
        RemoteCallback callback = new RemoteCallback();
        String cmd = buildCmd(reqExecuteVO);
        remoteShellExecutor.exec(cmd, callback);
        if (!StringUtils.isEmpty(callback.getStderrString())) {
            throw new RuntimeException(callback.getStderrString().trim());
        }

        // 保存执行记录信息
        ScriptEntity scriptEntity = this.getById(reqExecuteVO.getId());
        ExecRecordEntity recordEntity = new ExecRecordEntity();
        recordEntity.setScriptName(scriptEntity.getName());
        recordEntity.setScriptId(scriptEntity.getId());
        recordEntity.setContent(cmd);
        recordEntity.setName(scriptEntity.getName() + "-" + LocalDateTime.now());
        recordEntity.setStatus(0);
        recordEntity.buildAddEntity();
        execRecordService.save(recordEntity);

    }

    @Override
    public String getCmdInfo(ReqExecuteVO reqExecuteVO) {
        return buildCmd(reqExecuteVO);
    }

    private String buildCmd(ReqExecuteVO reqExecuteVO) {
        Integer scriptId = reqExecuteVO.getId();
        ScriptEntity scriptEntity = getById(scriptId);
        StringBuilder cmdBuilder = new StringBuilder(scriptEntity.getContent());
        List<Integer> expandIdList = reqExecuteVO.getExpandIdList();
        if (!CollectionUtils.isEmpty(expandIdList)) {
            scriptExpandService.lambdaQuery()
                    .in(ScriptExpandEntity::getId, expandIdList)
                    .orderByDesc(ScriptExpandEntity::getWeight)
                    .list()
                    .forEach(expand -> cmdBuilder.append(addBlank(expand.getContent())));
        }
        if (!StringUtils.isEmpty(reqExecuteVO.getExtraInfo())) {
            cmdBuilder.append(addBlank(reqExecuteVO.getExtraInfo()));
        }
        return cmdBuilder.toString();
    }

    private String addBlank(String str) {
        if (str.startsWith(" ")) {
            return str;
        }
        return " " + str;
    }

}
