package com.macro.mall.tiny.modules.map.dto;

import lombok.Data;

@Data
public class Results {

    private String name;
    private LocationDTO location;
    private String address;
    private String province;
    private String city;
    private String area;
    private int detail;
    private String uid;
}
