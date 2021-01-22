package hiram.acl.pojo.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/10/3 9:38
 * @Description: ""
 */

@Data
public class UserVO {

    private Long userId;

    private String username;

    private String nickname;

    private String realName;

    private String sex;

    private LocalDate birthday;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String remark;

    private Boolean enabled;
}
