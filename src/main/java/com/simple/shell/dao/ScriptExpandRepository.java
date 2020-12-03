package com.simple.shell.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.shell.pojo.ScriptExpandEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ScriptExpandRepository
 *
 * @author wuji
 * @date 2020-12-01 22:52
 */
@Mapper
public interface ScriptExpandRepository extends BaseMapper<ScriptExpandEntity> {
}