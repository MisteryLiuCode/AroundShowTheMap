package com.macro.mall.tiny.modules.map.service;

import com.macro.mall.tiny.modules.map.model.MapFileDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author macro
 * @since 2024-03-13
 */
public interface MapFileService extends IService<MapFileDO> {

    List<MapFileDO> getByFileId(String fileId);
}
