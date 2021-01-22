package hiram;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @Author: HiramHe
 * @Date: 2021/1/20 10:33
 * @Description: ""
 */

@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void encryptTest(){

        String mysqlOriginPwd = "your password";
        String redisOriginPwd = "your password";

        String mysqlEncryptPwd = jasyptStringEncryptor.encrypt(mysqlOriginPwd);
        String redisEncryptPwd = jasyptStringEncryptor.encrypt(redisOriginPwd);

        System.out.println("mysqlEncryptPwd:" + mysqlEncryptPwd);
        System.out.println("redisEncryptPwd:" + redisEncryptPwd);
    }

    @Test
    public void decryptTest(){

        Environment environment = applicationContext.getBean(Environment.class);

        String mysqlOriginPwd = environment.getProperty("spring.datasource.password");
        String redisOriginPwd = environment.getProperty("spring.redis.password");

        System.out.println("mysqlOriginPwd:" + mysqlOriginPwd);
        System.out.println("redisOriginPwd:" + redisOriginPwd);
    }
}
