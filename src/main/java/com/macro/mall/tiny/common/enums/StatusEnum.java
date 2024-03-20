package com.macro.mall.tiny.common.enums;

import lombok.Getter;

import java.util.Objects;


@Getter
public enum StatusEnum {
    // 禁用
    DISABLE(0, "禁用"),
    // 启用
    ENABLE(1, "启用"),
    ;

    private Integer code;

    private String text;

    StatusEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public static StatusEnum of(Integer code) {
        if (Objects.nonNull(code)) {
            for (StatusEnum e : values()) {
                if (e.getCode() == code) {
                    return e;
                }
            }
        }
        return null;
    }

    public boolean isMatch(Integer code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return Objects.equals(this.code, code);
    }
}
