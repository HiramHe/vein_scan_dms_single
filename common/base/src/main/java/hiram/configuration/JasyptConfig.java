package hiram.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 14:33
 * @Description: ""
 */

@Configuration
public class JasyptConfig {

    //name属性必须指定。默认"jasyptStringEncryptor"，如果为其他值，则需要到yml中配置jasypt.encryptor.bean，值即为这里的name值
    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor StringEncryptor(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("hiram");
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(1);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);

        return encryptor;
    }

}
