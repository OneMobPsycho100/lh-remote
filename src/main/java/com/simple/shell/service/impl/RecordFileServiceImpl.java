package com.simple.shell.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.shell.dao.RecordFileRepository;
import com.simple.shell.pojo.RecordFileEntity;
import com.simple.shell.service.IRecordFileService;
import com.simple.shell.vo.ResRecordFileVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RecordFileServiceImpl
 *
 * @author wuji
 * @date 2020-11-30 23:24
 */
@Service
public class RecordFileServiceImpl extends ServiceImpl<RecordFileRepository, RecordFileEntity> implements IRecordFileService {

    @Override
    public List<ResRecordFileVO> listRecordFileByRecordId(Integer id) {
        return this.lambdaQuery().eq(RecordFileEntity::getRecordId, id).list()
                .stream()
                .map(it -> {
                    ResRecordFileVO recordFileVO = new ResRecordFileVO();
                    BeanUtils.copyProperties(it, recordFileVO);
                    return recordFileVO;
                }).collect(Collectors.toList());
    }
}
