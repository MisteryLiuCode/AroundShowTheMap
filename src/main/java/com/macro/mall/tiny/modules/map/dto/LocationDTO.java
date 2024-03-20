package com.macro.mall.tiny.modules.map.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private double lng;
    private double lat;

    // 去重时为了方便数据映射 此数据在这展示,实际上数据返回时在上一级
    private String name;
}
