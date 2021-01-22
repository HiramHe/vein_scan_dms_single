package hiram.oss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 21:02
 * @Description: ""
 */

@SpringBootTest
public class OssTest {

    @Test
    public void testLocalDateFormat(){
        //把文件按照日期进行分类
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String datePath = now.format(formatter);

        System.out.println(datePath);
    }

}
