package com.macro.mall.tiny.common.enums;

import lombok.Getter;

@Getter
public enum DeletedStatusEnum {
    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除")
    ;

    private Integer code;

    private String name;

    DeletedStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getValue(Integer code) {
        DeletedStatusEnum[] statusEnums = values();
        for (DeletedStatusEnum statusEnum : statusEnums) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getName();
            }
        }
        return null;
    }
}
