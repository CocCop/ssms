package abc.red1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //开启注解方式的事务管理
public class Red1Application {

    public static void main(String[] args) {
        SpringApplication.run(Red1Application.class, args);
        log.info("系统启动，前端网址： http://localhost:8080/front/pages/login.html");
    }

}
