package com.simple.shell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.shell.pojo.RecordFileEntity;
import com.simple.shell.vo.ResRecordFileVO;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * IRecordFileService
 *
 * @author wuji
 * @date 2020-11-30 23:22
 */
public interface IRecordFileService extends IService<RecordFileEntity> {

    /**
     * 执行记录文件列表
     *
     * @param id 执行记录ID
     * @return List<ResRecordFileVO>
     */
    List<ResRecordFileVO> listRecordFileByRecordId(Integer id);

    /**
     * 基本生成文件下载
     *
     * @param id 文件id
     * @return Resource 资源文件
     */
    Resource downloadRecordFile(Integer id);
}