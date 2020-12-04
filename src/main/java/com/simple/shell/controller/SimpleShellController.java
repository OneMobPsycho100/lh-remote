package com.simple.shell.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.shell.common.Result;
import com.simple.shell.config.RemoteShellExecutor;
import com.simple.shell.vo.ResScriptInfoVO;
import com.simple.shell.vo.req.ReqExecuteVO;
import com.simple.shell.vo.req.ReqScriptInfoVO;
import com.simple.shell.service.IScriptExpandService;
import com.simple.shell.service.IScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

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

    @Autowired
    public SimpleShellController(RemoteShellExecutor remoteShellExecutor,
                                 IScriptService scriptService,
                                 IScriptExpandService scriptExpandService) {
        this.remoteShellExecutor = remoteShellExecutor;
        this.scriptService = scriptService;
        this.scriptExpandService = scriptExpandService;
    }

    @RequestMapping("/upload")
    public Result<String> upload(@RequestParam("scriptFile") MultipartFile file) {
        String filePath = getPath(path);
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            if (!targetFile.mkdirs()) {
                return Result.getFailure("文件上传失败");
            }
        }
        try (FileOutputStream out = new FileOutputStream(filePath + file.getOriginalFilename());) {
            byte[] bytes = file.getBytes();
            out.write(bytes);
            String cmd = new String(bytes);

            String[] result = new String[2];
            remoteShellExecutor.exec(cmd, new RemoteShellExecutor.Callback() {
                @Override
                public void getStdoutString(String stdoutString) {
                    result[0] = stdoutString;
                }

                @Override
                public void getStderrString(String stderrString) {
                    result[1] = stderrString;
                }
            });
            return Result.getSuccess(Arrays.toString(result));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getFailure("文件上传失败");
        }
    }

    private static String getPath(String path) {
        if (StringUtils.hasText(path)) {
            path = path.trim();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }


    @PostMapping("/save")
    public Result<String> save(@RequestBody ReqScriptInfoVO scriptInfoVO) {
        scriptService.saveScriptInfo(scriptInfoVO);
        return Result.getSuccess();
    }

    @GetMapping("/index/{page}/{size}")
    public Result<IPage<ResScriptInfoVO>> index(@PathVariable Long page, @PathVariable Long size) {
        IPage<ResScriptInfoVO> scriptInfoVOIPage = scriptService.pageScript(new Page<>(page, size));
        return Result.getSuccess(scriptInfoVOIPage);
    }

    @GetMapping("/info/{id}")
    public Result<ResScriptInfoVO> info(@PathVariable Integer id) {
        ResScriptInfoVO resScriptInfoVO = scriptService.scriptInfo(id);
        return Result.getSuccess(resScriptInfoVO);
    }

    @PutMapping("/edit")
    public Result<String> edit(@RequestBody ReqScriptInfoVO reqScriptInfoVO) {
        scriptService.updateScriptInfo(reqScriptInfoVO);
        return Result.getSuccess();
    }

    @DeleteMapping("/remove/{id}")
    public Result<String> remove(@PathVariable Integer id) {
        scriptService.removeById(id);
        return Result.getSuccess();
    }


    @DeleteMapping("/remove/expand/{id}")
    public Result<String> removeExpand(@PathVariable Integer id) {
        scriptExpandService.removeById(id);
        return Result.getSuccess();
    }


    @PostMapping("/execute")
    public Result<String> execute(@RequestBody ReqExecuteVO reqExecuteVO) throws Exception {
        scriptService.execute(reqExecuteVO);
        return Result.getSuccess();
    }


}
