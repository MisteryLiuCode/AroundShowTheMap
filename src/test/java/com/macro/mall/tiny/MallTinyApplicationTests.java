package com.macro.mall.tiny;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MallTinyApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        String centerLocation = String.format("%.3f,%.3f", Double.valueOf("40.00927012590208"), Double.valueOf("116.49329956037671"));
        System.out.println(centerLocation);
    }

}
