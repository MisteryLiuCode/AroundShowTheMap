package com.macro.mall.tiny.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FoodEnum {

    /**
     *
     * 附近美食$小吃快餐$中餐馆$自助餐$火锅$烧烤$奶茶$早餐$咖啡厅$面馆
     */
    NEARBY_FOOD("附近美食"),
    SMALL_FOOD("小吃快餐"),
    MIDDLE_FOOD("中餐馆"),
    OWN_FOOD("自助餐"),
    HOT_FOOD("火锅"),
    FRIED_FOOD("烧烤"),
    MILK_FOOD("奶茶"),
    BREAKFAST("早餐"),
    COFFEE_SHOP("咖啡厅"),
    RESTAURANT("面馆"),;


    /**
     * 美食类型
     */
    private final String foodName;


    FoodEnum(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public static List<String> getFoodNames() {
        return Arrays.stream(FoodEnum.values())
                .map(FoodEnum::getFoodName)
                .collect(Collectors.toList());
    }
}

