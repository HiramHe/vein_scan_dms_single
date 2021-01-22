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

        String accessKeySecret = "your accessKeySecret";

        String mysqlEncryptPwd = jasyptStringEncryptor.encrypt(mysqlOriginPwd);
        String redisEncryptPwd = jasyptStringEncryptor.encrypt(redisOriginPwd);
        String accessKeySecretEncrypt = jasyptStringEncryptor.encrypt(accessKeySecret);

        System.out.println("mysqlEncryptPwd:" + mysqlEncryptPwd);
        System.out.println("redisEncryptPwd:" + redisEncryptPwd);
        System.out.println("accessKeySecretEncrypt:" + accessKeySecretEncrypt);
    }

    @Test
    public void decryptTest(){

        Environment environment = applicationContext.getBean(Environment.class);

        String mysqlOriginPwd = environment.getProperty("spring.datasource.password");
        String redisOriginPwd = environment.getProperty("spring.redis.password");
        String accessKeySecret = environment.getProperty("aliyun.oss.file.accessKeySecret");

        System.out.println("mysqlOriginPwd:" + mysqlOriginPwd);
        System.out.println("redisOriginPwd:" + redisOriginPwd);
        System.out.println("accessKeySecret:" + accessKeySecret);
    }
}
