package com.simple.shell.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.shell.common.Result;
import com.simple.shell.config.RemoteShellExecutor;
import com.simple.shell.service.IRecordFileService;
import com.simple.shell.service.IScriptExpandService;
import com.simple.shell.service.IScriptService;
import com.simple.shell.vo.ResScriptInfoVO;
import com.simple.shell.vo.req.ReqExecuteVO;
import com.simple.shell.vo.req.ReqScriptInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SimpleShellController
 *
 * @author wuji
 * @date 2020-11-03 22:54
 */
@RestController
public class SimpleShellController {

    @Value("${spring.upload.path}")
    private String path;

    private final RemoteShellExecutor remoteShellExecutor;

    private final IScriptService scriptService;

    private final IScriptExpandService scriptExpandService;

    private final IRecordFileService recordFileService;

    @Autowired
    public SimpleShellController(RemoteShellExecutor remoteShellExecutor,
                                 IScriptService scriptService,
                                 IScriptExpandService scriptExpandService,
                                 IRecordFileService recordFileService) {
        this.remoteShellExecutor = remoteShellExecutor;
        this.scriptService = scriptService;
        this.scriptExpandService = scriptExpandService;
        this.recordFileService = recordFileService;
    }

    /**
     * 保存脚本信息
     *
     * @param scriptInfoVO 脚本及扩展信息
     * @return success
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody ReqScriptInfoVO scriptInfoVO) {
        scriptService.saveScriptInfo(scriptInfoVO);
        return Result.getSuccess();
    }

    /**
     * 分页查询脚本列表
     *
     * @param page 当前页
     * @param size 页大小
     * @return 脚本列表
     */
    @GetMapping("/index/{page}/{size}")
    public Result<IPage<ResScriptInfoVO>> index(@PathVariable Long page, @PathVariable Long size) {
        IPage<ResScriptInfoVO> scriptInfoVOIPage = scriptService.pageScript(new Page<>(page, size));
        return Result.getSuccess(scriptInfoVOIPage);
    }

    /**
     * 查看脚本详细信息，包括附加信息
     *
     * @param id 脚本id
     * @return 脚本详细信息
     */
    @GetMapping("/info/{id}")
    public Result<ResScriptInfoVO> info(@PathVariable Integer id) {
        ResScriptInfoVO resScriptInfoVO = scriptService.scriptInfo(id);
        return Result.getSuccess(resScriptInfoVO);
    }

    /**
     * 编辑脚本
     *
     * @param reqScriptInfoVO 脚本信息
     * @return success
     */
    @PutMapping("/edit")
    public Result<String> edit(@RequestBody ReqScriptInfoVO reqScriptInfoVO) {
        scriptService.updateScriptInfo(reqScriptInfoVO);
        return Result.getSuccess();
    }

    /**
     * 删除脚本
     *
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public Result<String> remove(@PathVariable Integer id) {
        scriptService.removeById(id);
        return Result.getSuccess();
    }


    /**
     * 删除脚本的附加信息
     *
     * @param id 附加信息id
     * @return success
     */
    @DeleteMapping("/remove/expand/{id}")
    public Result<String> removeExpand(@PathVariable Integer id) {
        scriptExpandService.removeById(id);
        return Result.getSuccess();
    }

    /**
     * 查看完整的脚本
     *
     * @param reqExecuteVO 脚本附加信息
     * @return 脚本完整信息
     */
    @PostMapping("/cmd/info")
    public Result<String> cmdInfo(@RequestBody ReqExecuteVO reqExecuteVO) {
        String cmd = scriptService.getCmdInfo(reqExecuteVO);
        return Result.getSuccess(cmd);
    }

    /**
     * 执行脚本
     *
     * @param reqExecuteVO 脚本信息（扩展信息）
     * @return success
     * @throws Exception ssh
     */
    @PostMapping("/execute")
    public Result<String> execute(@RequestBody ReqExecuteVO reqExecuteVO) throws Exception {
        scriptService.execute(reqExecuteVO);
        return Result.getSuccess();
    }

    /**
     * 文件下载
     *
     * @param id 文件id
     * @return 文件资源
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) {
        Resource resource = recordFileService.downloadRecordFile(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
