package hiram.utils.token;

/**
 * @Author: HiramHe
 * @Date: 2020/5/5 13:54
 * @Description: ""
 */

import lombok.Data;

import java.util.Date;

/**
 * jwt的载荷
 */

@Data
public class Payload<T> {
    private String id;
    private T object;
    private Date expiration;
}
