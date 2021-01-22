package hiram.acl.pojo.vo;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/4/23 21:18
 * @Description: "修改密码的vo"
 */

@Data
public class UserPasswordVO {

    private String oldPassword;

    private String newPassword;
}
