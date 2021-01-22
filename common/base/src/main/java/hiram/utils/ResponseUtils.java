package hiram.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hiram.pojo.vo.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: HiramHe
 * @Date: 2020/4/29 16:48
 * @Description: ""
 */
public class ResponseUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    public static void writeObject(HttpServletResponse response, ResultObject resultObject) {

        //很重要，否则前端获取不到JSON
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(resultObject.getHttpCode());
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Method","POST,GET");

        //输出json
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(resultObject));

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
}
