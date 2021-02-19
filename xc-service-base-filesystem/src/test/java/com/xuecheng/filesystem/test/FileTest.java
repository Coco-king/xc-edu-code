package com.xuecheng.filesystem.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {
    @Test
    public void testExt() {
        String fileName = "test.png";
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(fileExtName);
    }
}
