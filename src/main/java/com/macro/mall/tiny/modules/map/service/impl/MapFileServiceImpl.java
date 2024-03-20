package com.macro.mall.tiny.modules.map.service.impl;

import com.macro.mall.tiny.modules.map.model.MapFileDO;
import com.macro.mall.tiny.modules.map.mapper.MapFileMapper;
import com.macro.mall.tiny.modules.map.service.MapFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author macro
 * @since 2024-03-13
 */
@Service
public class MapFileServiceImpl extends ServiceImpl<MapFileMapper, MapFileDO> implements MapFileService {

    @Resource
    private MapFileMapper mapFileMapper;

    @Override
    public List<MapFileDO> getByFileId(String fileId) {

        return mapFileMapper.getByFileId(fileId);
    }
}
