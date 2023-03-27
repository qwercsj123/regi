package com.csj.regi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegiApplicationTests {

    @Test
    void contextLoads() {
        String str="/dish/status/1";
        String substring = str.substring(str.lastIndexOf("/")+1);
        System.out.println(substring);
    }

}
