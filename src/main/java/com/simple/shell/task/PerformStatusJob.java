package com.simple.shell.task;

import com.simple.shell.pojo.ScriptExpandEntity;
import com.simple.shell.service.IScriptExpandService;
import com.simple.shell.utils.Utils;
import com.simple.shell.config.RemoteCallback;
import com.simple.shell.config.RemoteShellExecutor;
import com.simple.shell.config.RemoteShellProperties;
import com.simple.shell.pojo.ExecRecordEntity;
import com.simple.shell.pojo.RecordFileEntity;
import com.simple.shell.service.IExecRecordService;
import com.simple.shell.service.IRecordFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * PerformStatusJob
 *
 * @author wuji
 * @date 2020-12-06 17:22
 */
@Component
public class PerformStatusJob {

    private final Logger logger = LoggerFactory.getLogger(PerformStatusJob.class);

    private final IExecRecordService execRecordService;

    private final RemoteShellExecutor remoteShellExecutor;

    private final IRecordFileService recordFileService;

    private final RemoteShellProperties properties;

    private final IScriptExpandService scriptExpandService;

    private static final String FIND = "find ";

    @Autowired
    public PerformStatusJob(IExecRecordService execRecordService,
                            RemoteShellExecutor remoteShellExecutor,
                            IRecordFileService recordFileService,
                            RemoteShellProperties properties, IScriptExpandService scriptExpandService) {
        this.execRecordService = execRecordService;
        this.remoteShellExecutor = remoteShellExecutor;
        this.recordFileService = recordFileService;
        this.properties = properties;
        this.scriptExpandService = scriptExpandService;
    }

    /**
     * 定时查询脚本任务是否执行成功 30分钟执行一次
     */
    // @Scheduled(cron = "0 0/30 * * * ? ")
    //  @Scheduled(cron = "0/10 * * * * ?")
    private void performStatus() {

        try {
            List<ExecRecordEntity> recordList = execRecordService.lambdaQuery()
                    .eq(ExecRecordEntity::getStatus, 0).list();
            if (CollectionUtils.isEmpty(recordList)) {
                return;
            }
            // 远程执行shell 判断是否执行成功 FIXME 怎么判断执行成功了？
            RemoteCallback callback = new RemoteCallback();
            remoteShellExecutor.exec(FIND + Utils.getPath(properties.getComplete()), callback);
            String stdoutString = callback.getStdoutString();
            if (stdoutString == null || stdoutString.contains(FIND)) {
                return;
            }
            ExecRecordEntity execRecordEntity = recordList.get(0);
            execRecordEntity.setStatus(1);
            execRecordService.updateById(execRecordEntity);

            // 脚本执行成功就将服务器文件转存，便于下载
            String local = properties.getLocal();
            String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
            Integer recordId = execRecordEntity.getId();
            String prefix = local + "/" + today + recordId;
            for (String path : properties.getFilePath()) {
                remoteShellExecutor.download(path, prefix);
                RecordFileEntity fileEntity = new RecordFileEntity();
                fileEntity.setRecordId(recordId);
                String[] split = path.split("/");
                fileEntity.setName(split[split.length - 1]);
                recordFileService.save(fileEntity);
            }
        } catch (Exception e) {
            logger.error("定时任务执行出错：", e);
        }

    }


 //   @Scheduled(cron = "0/10 * * * * ?")
    private void performStatusViolence() {

        try {
            List<ExecRecordEntity> recordList = execRecordService.lambdaQuery()
                    .eq(ExecRecordEntity::getStatus, 0).list();
            if (CollectionUtils.isEmpty(recordList)) {
                return;
            }
            ExecRecordEntity execRecordEntity = recordList.get(0);

            List<ScriptExpandEntity> expandList = scriptExpandService.lambdaQuery()
                    .eq(ScriptExpandEntity::getScriptId, execRecordEntity.getScriptId()).eq(ScriptExpandEntity::getType, 0).list();
            if (CollectionUtils.isEmpty(expandList)) {
                return;
            }

            // 脚本执行成功就将服务器文件转存，便于下载
            for (ScriptExpandEntity expandEntity : expandList) {
                String content = expandEntity.getContent();
                String[] filePathArr = content.split(",");
                String remote = filePathArr[0];
                String local = filePathArr[1];
                String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
                Integer recordId = execRecordEntity.getId();
                String prefix = local + today + recordId + "\\";
                String fileName = remoteShellExecutor.download(remote, prefix);
                RecordFileEntity fileEntity = new RecordFileEntity();
                fileEntity.setRecordId(recordId);
                fileEntity.setName(expandEntity.getName());
                fileEntity.setAddress(prefix + fileName);
                recordFileService.save(fileEntity);
            }

            execRecordEntity.setStatus(1);
            execRecordService.updateById(execRecordEntity);
        } catch (Exception e) {
            logger.error("定时任务执行出错：", e);
        }

    }

}
