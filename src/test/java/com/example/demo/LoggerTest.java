package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LoggerTest extends DemoApplicationTests {

    @Test
    public void testLog(){
        String name1 = "油大";
        String name2 = "油二";
        log.info("name1:{}",name1);
        log.info("name2:{}",name2);
        log.error("error");
        log.debug("debug");
        log.trace("trace");
        log.warn("warn");
    }
}
