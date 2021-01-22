package hiram;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.*;

/**
 * @Author: HiramHe
 * @Date: 2021/1/22 10:17
 * @Description: ""
 */


public class TimeTest {

    @Test
    public void MillisTest(){

        //获得的是自1970年1月1日0时起的毫秒数
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("currentTimeMillis:" + currentTimeMillis);

        String patten = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
        String format = dateFormat.format(currentTimeMillis);
        System.out.println("format:" + format);

        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC).plusHours(8);
        System.out.println("localDateTime:" + localDateTime);

        LocalTime localTime = LocalTime.now(ZoneOffset.UTC);
        System.out.println("localTime:" + localTime);

        ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
        long epochMilli = LocalDateTime.now().toInstant(zoneOffset).toEpochMilli();
        System.out.println("epochMilli:" + epochMilli);

        LocalDateTime milli2LocalDatetime = LocalDateTime.ofEpochSecond(currentTimeMillis/1000, 0, zoneOffset);
        System.out.println("milli2LocalDatetime:" + milli2LocalDatetime);

        System.out.println(LocalDateTime.now(ZoneOffset.UTC).plusHours(8).toString());

        LocalDateTime now = LocalDateTime.now();
        System.out.println("now:" + now);
        System.out.println("zoneOffset:" + zoneOffset);

        long l = now.toInstant(zoneOffset).toEpochMilli();
        LocalDateTime localDateTime1 = LocalDateTime.ofEpochSecond(l / 1000, 0, zoneOffset);
        System.out.println("localDateTime1:" + localDateTime1);
    }

}
