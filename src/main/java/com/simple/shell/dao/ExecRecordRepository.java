package com.simple.shell.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.shell.pojo.ExecRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ExecRecordRepository
 *
 * @author wuji
 * @date 2020-12-02 22:11
 */
@Mapper
public interface ExecRecordRepository extends BaseMapper<ExecRecordEntity> {
}