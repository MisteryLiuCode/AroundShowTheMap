package com.macro.mall.tiny.modules.map.mapper;

import com.macro.mall.tiny.modules.map.model.MapFileDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2024-03-13
 */
public interface MapFileMapper extends BaseMapper<MapFileDO> {

    List<MapFileDO> getByFileId(@Param("fileId") String fileId);
}
