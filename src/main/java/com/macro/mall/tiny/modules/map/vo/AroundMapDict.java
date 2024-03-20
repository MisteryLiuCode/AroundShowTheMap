package com.macro.mall.tiny.modules.map.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value="地图字典", description="地图字典")
public class AroundMapDict {

    @NotEmpty
    @ApiModelProperty(value = "美食分类")
    private List<String> foods;

}
