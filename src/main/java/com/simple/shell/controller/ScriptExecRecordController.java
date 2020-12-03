package com.simple.shell.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.simple.shell.common.Result;
import com.simple.shell.vo.ResExecRecordVO;
import com.simple.shell.vo.ResRecordFileVO;
import com.simple.shell.vo.req.ReqRecordSearchVO;
import com.simple.shell.service.IExecRecordService;
import com.simple.shell.service.IRecordFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ScriptExecRecordController
 *
 * @author wuji
 * @date 2020-12-02 21:42
 */
@RestController
@RequestMapping("/record")
public class ScriptExecRecordController {


    private final IExecRecordService execRecordService;

    private final IRecordFileService recordFileService;

    @Autowired
    public ScriptExecRecordController(IExecRecordService execRecordService, IRecordFileService recordFileService) {
        this.execRecordService = execRecordService;
        this.recordFileService = recordFileService;
    }

    @RequestMapping("/index")
    public Result<IPage<ResExecRecordVO>> index(@RequestBody ReqRecordSearchVO recordSearchVO) {
        IPage<ResExecRecordVO> result = execRecordService.pageExecRecordById(recordSearchVO);
        return Result.getSuccess(result);
    }

    @GetMapping("/info/{id}")
    public Result<ResExecRecordVO> info(@PathVariable Integer id) {
        ResExecRecordVO recordVO = execRecordService.info(id);
        return Result.getSuccess(recordVO);
    }

    @GetMapping("/list/{id}")
    public Result<List<ResRecordFileVO>> list(@PathVariable Integer id) {
        List<ResRecordFileVO> fileVOList = recordFileService.listRecordFileByRecordId(id);
        return Result.getSuccess(fileVOList);
    }

}
