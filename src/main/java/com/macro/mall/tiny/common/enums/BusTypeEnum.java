package com.macro.mall.tiny.common.enums;

import lombok.Getter;

import java.util.Objects;


/**
 * 业务类型
 */
@Getter
public enum BusTypeEnum {
    // 美食
    FOOD(10, "food"),
    // 超市
    SHOP(20, "shop"),
    // 学校
    SCHOOL(30, "school"),
    // 自定义
    CUSTOM(40, "custom")

    ;

    private int code;

    private String text;

    BusTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static BusTypeEnum of(int code) {
        if (Objects.nonNull(code)) {
            for (BusTypeEnum e : values()) {
                if (e.getCode() == code) {
                    return e;
                }
            }
        }
        return null;
    }

    public boolean isMatch(int code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.equals(this.code, code);
    }
}
