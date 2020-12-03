package com.simple.shell.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.shell.pojo.RecordFileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * RecordFileRepository
 *
 * @author wuji
 * @date 2020-12-02 22:56
 */
@Mapper
public interface RecordFileRepository extends BaseMapper<RecordFileEntity> {
}