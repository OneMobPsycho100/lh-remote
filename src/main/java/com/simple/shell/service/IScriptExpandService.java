package com.simple.shell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simple.shell.pojo.ScriptExpandEntity;
import com.simple.shell.vo.ResScriptExpandVO;

import java.util.List;

/**
 * IScriptExpandService
 *
 * @author wuji
 * @date 2020-11-30 23:23
 */
public interface IScriptExpandService extends IService<ScriptExpandEntity> {

    /**
     * 查询脚本扩展信息
     * @param id
     * @return
     */
    List<ResScriptExpandVO> listExpandByScriptId(Integer id);
}