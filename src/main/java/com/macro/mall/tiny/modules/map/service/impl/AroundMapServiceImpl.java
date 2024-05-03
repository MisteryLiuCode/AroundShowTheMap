package com.macro.mall.tiny.modules.map.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.macro.mall.tiny.common.enums.BusTypeEnum;
import com.macro.mall.tiny.common.enums.DeletedStatusEnum;
import com.macro.mall.tiny.common.enums.FoodEnum;
import com.macro.mall.tiny.common.enums.StatusEnum;
import com.macro.mall.tiny.common.service.OSSUnity;
import com.macro.mall.tiny.modules.map.dto.*;
import com.macro.mall.tiny.modules.map.model.MapFileDO;
import com.macro.mall.tiny.modules.map.service.AroundMapService;
import com.macro.mall.tiny.modules.map.service.MapFileService;
import com.macro.mall.tiny.modules.map.utils.Constants;
import com.macro.mall.tiny.modules.map.vo.AroundMapDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AroundMapServiceImpl implements AroundMapService {

    @Resource
    private MapFileService mapFileService;

    @Autowired
    private OSSUnity ossUnity;

    private final String searchUrl = "https://api.map.baidu.com/place/v2/search?query=";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getAround(AroundMapDTO aroundMapDTO) {
        // 校验参数
        Boolean vaild = CollectionUtils.isEmpty(aroundMapDTO.getSchoolKeys()) && CollectionUtils.isEmpty(aroundMapDTO.getFoodKeys()) && CollectionUtils.isEmpty(aroundMapDTO.getShopKeys()) && CollectionUtils.isEmpty(aroundMapDTO.getCustomKeys());
        if (vaild) {
            return "参数错误";
        }
        // 初始化基本路径和文件名信息
        String randomFileNameId = UUID.randomUUID().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = dateFormat.format(new Date());
        String basePath = "/Users/liushuaibiao/Documents/aroundMap/data/";
        String centerLocation = String.format("%.3f,%.3f", Double.valueOf(aroundMapDTO.getLatitude()), Double.valueOf(aroundMapDTO.getLongitude()));

        if (CollectionUtils.isNotEmpty(aroundMapDTO.getFoodKeys())) {
            processData(BusTypeEnum.FOOD, randomFileNameId, folderName, basePath, aroundMapDTO.getFoodKeys(),
                    centerLocation);
        }
        if (CollectionUtils.isNotEmpty(aroundMapDTO.getShopKeys())) {
            processData(BusTypeEnum.SHOP, randomFileNameId, folderName, basePath, aroundMapDTO.getShopKeys(),
                    centerLocation);
        }
        if (CollectionUtils.isNotEmpty(aroundMapDTO.getSchoolKeys())) {
            processData(BusTypeEnum.SCHOOL, randomFileNameId, folderName, basePath, aroundMapDTO.getSchoolKeys(),
                    centerLocation);
        }
        if (CollectionUtils.isNotEmpty(aroundMapDTO.getCustomKeys())) {
            processData(BusTypeEnum.CUSTOM, randomFileNameId, folderName, basePath, aroundMapDTO.getCustomKeys(),
                    centerLocation);
        }

        // 返回id
        return Constants.url + randomFileNameId;
    }

    private void processData(BusTypeEnum busTypeEnum, String randomFileNameId, String folderName, String basePath,
                             List<String> keys, String centerLocation) {
        String fileName = folderName + "/" + randomFileNameId + busTypeEnum.getText() + ".json";
        String originalPath = basePath + fileName;
        // 创建文件
        File file = createFile(originalPath);
        String ossPath;
        String searchParams;
        String tag ="";
        String industry_type = "";
        if (BusTypeEnum.FOOD.equals(busTypeEnum) && keys.contains("all")) {
            searchParams = "美食,附近美食";
//            tag = "美食";
            industry_type = "cater";
        } else if (BusTypeEnum.SHOP.equals(busTypeEnum) && keys.contains("all")) {
            searchParams = "购物";
            tag = "购物中心、百货商场、超市、便利店";
            industry_type = "life";
        } else {
            // 组装搜索参数
            searchParams = String.join(",", keys);
        }
        // 搜索结果放入json
        JSONArray jsonArray = new JSONArray();
        tag = "".equals(tag) ? "" : "&tag="+tag;
        industry_type = "".equals(industry_type) ? "" : "&industry_type="+industry_type;
        try (FileWriter writer = new FileWriter(originalPath)) {
            String firstUrl = searchUrl + searchParams + "&location=" + centerLocation + "&radius=2000&output=json&ak=d6KFBCpxKoA7Y1i3JCOpBMmsPq4lm7tI&page_num=1&page_size=20&scope=2" + tag + industry_type+"&filter=sort_name:distance|sort_rule:1";
            log.info("firstUrl:{}", firstUrl);
            String firstResp = HttpUtil.createGet(firstUrl).execute().body();
            CircleLocationDTO firstLocation = JSONObject.parseObject(firstResp, CircleLocationDTO.class);
            // 总页数
            int pageNum = firstLocation.getTotal() / 20;
            for (int i = 0; i < pageNum; i++) {
                String url = searchUrl + searchParams + "&location=" + centerLocation + "&radius=2500&output=json&ak=d6KFBCpxKoA7Y1i3JCOpBMmsPq4lm7tI&page_num=" + i + "&page_size=20&scope=2" + tag + industry_type+"&filter=sort_name:distance|sort_rule:1";
                String res = HttpUtil.createGet(url).execute().body();
                CircleLocationDTO circleLocation = JSONObject.parseObject(res, CircleLocationDTO.class);
                List<Results> results = circleLocation.getResults();
                for (int j = 0; j < results.size(); j++) {
                    Results result = results.get(j);
                    LocationDTO location = result.getLocation();
                    WriteJsonDTO writeJsonDto = new WriteJsonDTO();
                    BeanUtils.copyProperties(location, writeJsonDto);
                    writeJsonDto.setName(result.getName());
                    jsonArray.add(writeJsonDto);
                }
            }
            // 写入文件
            writer.write(jsonArray.toJSONString());
        } catch (IOException e) {
            log.error("数据写入失败", e);
            throw new RuntimeException("数据写入失败", e);
        }
        // 上传文件到oss
        try {
            ossPath = ossUnity.upload("file/" + fileName, file);
            System.out.println("上传文件到oss成功，ossPath：" + ossPath);
        } catch (Exception e) {
            log.error("上传文件到oss失败", e);
            throw new RuntimeException("上传文件到oss失败", e);
        } finally {
            // 删除文件
        }
        // 保存文件信息到数据库
        boolean saveSuccess = saveMapFileDO(randomFileNameId, fileName, ossPath, busTypeEnum.getCode(), centerLocation);
        if (!saveSuccess) {
            throw new RuntimeException("保存文件信息失败");
        }
    }

    private boolean saveMapFileDO(String randomFileNameId, String fileName, String ossPath, Integer busType,
                                  String centerLocation) {

        // 将数据存入数据库
        MapFileDO mapFileDO = new MapFileDO();
        mapFileDO.setFileId(randomFileNameId);
        mapFileDO.setUserId(1);
        mapFileDO.setFileName(fileName);
        mapFileDO.setPath("https://" + ossPath);
        mapFileDO.setCenterLocation(centerLocation);
        mapFileDO.setBusType(busType);
        mapFileDO.setStatus(StatusEnum.ENABLE.getCode());
        mapFileDO.setDeleted(DeletedStatusEnum.NOT_DELETED.getCode());
        mapFileDO.setGmtCreate(new Date());
        mapFileDO.setGmtModified(new Date());
        boolean save = mapFileService.save(mapFileDO);
        return save; // 返回保存结果
    }

    /**
     * 获取枚举
     *
     * @return
     */
    @Override
    public AroundMapDict getDict() {
        AroundMapDict dict = new AroundMapDict();
        // 美食分类
        dict.setFoods(FoodEnum.getFoodNames());
        return dict;
    }

    @Override
    public ModelAndView getDataPathById(String fileId) {
        ModelAndView modelAndView = new ModelAndView();
        List<MapFileDO> mapFileDOS = mapFileService.getByFileId(fileId);
        if (CollectionUtils.isNotEmpty(mapFileDOS)) {
            String centerLocation = mapFileDOS.get(0).getCenterLocation();
            // 以逗号分隔
            String[] centerLocationArr = centerLocation.split(",");
            modelAndView.addObject("centerLat", Double.valueOf(centerLocationArr[0]));
            modelAndView.addObject("centerLon", Double.valueOf(centerLocationArr[1]));
            mapFileDOS.forEach(mapFileDO -> addPathToModel(modelAndView, mapFileDO));
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("404"); // 如果列表为空，显示404页面
        }

        return modelAndView;
    }

    private void addPathToModel(ModelAndView modelAndView, MapFileDO mapFileDO) {
        switch (mapFileDO.getBusType()) {
            case 10:
                modelAndView.addObject("foodPath", mapFileDO.getPath());
                break;
            case 20:
                modelAndView.addObject("shopPath", mapFileDO.getPath());
                break;
            case 30:
                modelAndView.addObject("schoolPath", mapFileDO.getPath());
                break;
            case 40:
                modelAndView.addObject("customPath", mapFileDO.getPath());
                break;
            default:
                break;
        }
    }

    /**
     * 创建文件
     *
     * @param filePath
     */
    public File createFile(String filePath) {
        File file = new File(filePath);
        // 首先确保文件所在的目录存在
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        // 现在检查文件是否存在
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("File already exists or failed to create: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File already exists, skipping creation: " + file.getAbsolutePath());
        }
        return file;
    }
}
