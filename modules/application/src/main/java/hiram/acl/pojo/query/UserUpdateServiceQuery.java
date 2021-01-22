package hiram.acl.pojo.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/10/1 19:05
 * @Description: ""
 */

@Data
public class UserUpdateServiceQuery {

    private String username;

    private String nickname;

    private String realName;

    private String sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String remark;

    private Boolean enabled;

}
