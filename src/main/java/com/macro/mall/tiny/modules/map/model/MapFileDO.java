package com.macro.mall.tiny.modules.map.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * </p>
 *
 * @author macro
 * @since 2024-03-13
 */
@Getter
@Setter
@TableName("map_file")
@ApiModel(value = "MapFile对象", description = "保存文件对象")
public class MapFileDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @ApiModelProperty("文件id")
    private String fileId;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("admin_id")
    private Integer userId;

    @ApiModelProperty("存储路径")
    private String path;

    @ApiModelProperty("业务类型 eg,10:美食，20:超市")
    private int busType;

    @ApiModelProperty("中心位置 eg,39.999407,116.474647")
    private String centerLocation;

    @ApiModelProperty("状态，1=启动，0=禁用")
    private Integer status;

    @ApiModelProperty("是否软删，1是0否")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    public String getPath() {
        return path;
    }
}
