package com.macro.mall.tiny.modules.map.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AroundMapDTO {

    /**
     * 美食搜索关键词
     */
    @NotEmpty
    @ApiModelProperty(value = "美食搜索关键词")
    private List<String> foodKeys;

    /**
     * 超市搜索关键词
     */

    @ApiModelProperty(value = "超市搜索关键词")
    private List<String> shopKeys;

    /**
     * 学校搜索关键词
     */
    @ApiModelProperty(value = "学校搜索关键词")
    private List<String> schoolKeys;

    /**
     * 自定义搜索关键词
     */
    @ApiModelProperty(value = "自定义搜索关键词")
    private List<String> customKeys;

    @NotEmpty
    @ApiModelProperty(value = "中心位置经度",required = true)
    private String longitude;


    @NotEmpty
    @ApiModelProperty(value = "中心位置纬度",required = true)
    private String latitude;



}
