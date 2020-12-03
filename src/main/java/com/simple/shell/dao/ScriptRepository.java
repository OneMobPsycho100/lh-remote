package com.simple.shell.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.shell.pojo.ScriptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ScriptRepository
 *
 * @author wuji
 * @date 2020-12-01 22:07
 */
@Mapper
public interface ScriptRepository extends BaseMapper<ScriptEntity> {


}