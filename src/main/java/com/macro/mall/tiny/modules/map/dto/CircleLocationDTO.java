package com.macro.mall.tiny.modules.map.dto;

import lombok.Data;

import java.util.List;

@Data
public class CircleLocationDTO {

    private int status;
    private String status_message;
    private int total;
    private String result_type;
    private List<Results> results;
}
