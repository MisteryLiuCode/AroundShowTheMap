package com.macro.mall.tiny.modules.map.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.map.dto.AroundMapDTO;
import com.macro.mall.tiny.modules.map.service.AroundMapService;
import com.macro.mall.tiny.modules.map.vo.AroundMapDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@Slf4j
@Api(tags = "AroundMapController")
@Tag(name = "AroundMapController", description = "map")
@RequestMapping("/map")
public class AroundMapController {

    @Resource
    private AroundMapService aroundMapService;


    /**
     * 获取字典
     */
    @ApiOperation(value = "获取字典", notes = "获取字典")
    @RequestMapping(value = "/getDict", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<AroundMapDict> getDict() {
        log.info("开始获取字典");
        AroundMapDict dict = aroundMapService.getDict();
        return CommonResult.success(dict);
    }


    @ApiOperation(value = "获取周围地图", notes = "获取周围地图")
    @RequestMapping(value = "/around", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> around(@RequestBody @Valid AroundMapDTO aroundMapDTO) {
        // 打印参数
        log.info("开始获取周围地图, aroundMapDTO:{}", aroundMapDTO);
        String res = aroundMapService.getAround(aroundMapDTO);

        if (StringUtils.isNotBlank(res)) {
            return CommonResult.success(res);
        } else {
            return CommonResult.validateFailed(res);
        }
    }


    @ApiOperation(value = "获取地图", notes = "获取地图")
    @RequestMapping(value = "/getMap/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getMap(@PathVariable String fileId) {
        log.info("getMap, fileId: {}", fileId);
        return aroundMapService.getDataPathById(fileId);
    }


}
