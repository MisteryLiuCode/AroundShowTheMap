package com.macro.mall.tiny.modules.map.service;

import com.macro.mall.tiny.modules.map.dto.AroundMapDTO;
import com.macro.mall.tiny.modules.map.model.MapFileDO;
import com.macro.mall.tiny.modules.map.vo.AroundMapDict;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface AroundMapService {


    /**
     * 获取周边
     * @param aroundMapDTO 搜索入参
     * @return
     */
    String getAround(AroundMapDTO aroundMapDTO);

    /**
     * 获取系统字典
     */
    AroundMapDict getDict();


    ModelAndView getDataPathById(String id);
}
