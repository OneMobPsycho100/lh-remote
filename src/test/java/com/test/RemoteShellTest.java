package com.test;

import com.simple.shell.SimpleShellApplication;
import com.simple.shell.config.RemoteShellExecutor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test
 *
 * @author wuji
 * @date 2020-12-03 22:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SimpleShellApplication.class)
public class RemoteShellTest {

    @Autowired
    private RemoteShellExecutor remoteShellExecutor;

    @org.junit.Test
    public void test() throws Exception {
        String exec = remoteShellExecutor.download("/home/cmz/files.csv", "C:\\Users\\Administrator\\Desktop");
        System.out.println(exec);
    }
}
