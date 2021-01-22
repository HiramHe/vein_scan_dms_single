package hiram.ucenter.pojo.dto;

import hiram.pojo.po.BasePO;
import hiram.acl.pojo.po.SysRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/7/17 10:51
 * @Description: ""
 */

/*
rt = return
 */

@Data
public class UserSelectDTO extends BasePO {

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

    private List<SysRole> roles;
}
