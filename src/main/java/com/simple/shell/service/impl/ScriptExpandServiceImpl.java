package com.simple.shell.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.shell.dao.ScriptExpandRepository;
import com.simple.shell.pojo.ScriptExpandEntity;
import com.simple.shell.service.IScriptExpandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScriptExpandServiceImpl
 *
 * @author wuji
 * @date 2020-11-30 23:23
 */
@Service
public class ScriptExpandServiceImpl extends ServiceImpl<ScriptExpandRepository, ScriptExpandEntity> implements IScriptExpandService {

    @Override
    public List<ScriptExpandEntity> listExpandByScriptId(Integer id) {
        return this.lambdaQuery().eq(ScriptExpandEntity::getScriptId, id).list();
    }
}
