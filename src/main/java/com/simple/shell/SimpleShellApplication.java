package com.simple.shell;

import com.simple.shell.config.RemoteShellExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * SimpleShellApplication
 *
 * @author wuji
 * @date 2020-11-03 21:34
 */
@SpringBootApplication
@RestController
public class SimpleShellApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleShellApplication.class);
    }

    private final RemoteShellExecutor remoteShellExecutor;

    @Autowired
    public SimpleShellApplication(RemoteShellExecutor remoteShellExecutor, DataSource dataSource) {
        this.remoteShellExecutor = remoteShellExecutor;
        this.dataSource = dataSource;
    }

    @GetMapping("/test")
    public String test(@RequestParam String cmd) throws Exception {
        String [] result = new String[2];
        remoteShellExecutor.exec(cmd, new RemoteShellExecutor.Callback() {
            @Override
            public void getStdoutString(String stdoutString) {
                result[0] = stdoutString;
            }

            @Override
            public void getStderrString(String stderrString) {
                result[1] = stderrString;
            }
        });
        return Arrays.toString(result);
    }

    @RequestMapping("/test2")
    public String test2() {
        return "2--";
    }



    final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(dataSource.getConnection());
    }
}
