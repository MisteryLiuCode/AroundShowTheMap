package com.macro.mall.tiny.modules.map.utils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.tiny.modules.map.dto.LocationDTO;
import com.macro.mall.tiny.modules.map.dto.WriteJsonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 数据去重
 */
@Slf4j
public class DataDeduplicationUtil {
    public static void main(String[] args) {
        String path = "/Users/misteryliu/Documents/work/project/LinkDemo/circleData/school.json";
        String deduplicationPath = "/Users/misteryliu/Documents/work/project/LinkDemo/circleData/dataDeduplication/school.json";
        dataDeduplication(path, deduplicationPath);
    }


    public static boolean dataDeduplication(String path, String deduplicationPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 读取JSON文件
            List<LocationDTO> locationList = objectMapper.readValue(new File(path), new TypeReference<List<LocationDTO>>() {
            });
            System.out.println("去重前数据量：" + locationList.size());
            // 对lng和lat进行去重
            Set<Coordinate> uniqueCoordinates = new HashSet<>();
            List<LocationDTO> results = new ArrayList<>();

            for (LocationDTO data : locationList) {
                Coordinate coordinate = new Coordinate(data.getLng(), data.getLat());
                if (!uniqueCoordinates.contains(coordinate)) {
                    uniqueCoordinates.add(coordinate);
                    results.add(data);
                }
            }
            System.out.println("去重后数据量：" + results.size());
            JSONArray jsonArray = new JSONArray();
            // 将数据写入新的json文件
            try (FileWriter writer = new FileWriter(deduplicationPath)) {
                for (int i = 0; i < results.size(); i++) {
                    LocationDTO location = results.get(i);
                    WriteJsonDTO writeJsonDto = new WriteJsonDTO();
                    BeanUtils.copyProperties(location, writeJsonDto);
                    jsonArray.add(writeJsonDto);
                }
                writer.write(jsonArray.toJSONString());
                return true;
            } catch (IOException e) {
                log.error("数据写入失败", e);
                return false;
            }

//            // 遍历地区,统计区域数量
//            for (Location location : result) {
//                String url = "https://restapi.amap.com/v3/geocode/regeo?output=json&location=" + location.getLng() + "," + location.getLat() + "&key=0fb664b477f15256035f259fd406defc&radius=500&extensions=base";
//                String res = HttpUtil.createGet(url).execute().body();
//                GaodeLocation gaodeLocation = JSONObject.parseObject(res, GaodeLocation.class);
//                String district = gaodeLocation.getRegeocode().getAddressComponent().getDistrict();
//                if (countMap.containsKey(district)) {
//                    // 如果Map中已经存在该key，则将其value加1
//                    countMap.put(district, countMap.get(district) + 1);
//                } else {
//                    // 如果Map中不存在该key，则将其加入Map，并将value设为1
//                    countMap.put(district, 1);
//                }
//            }
//            // 打印统计结果
//            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
