package hiram.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import hiram.pojo.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 实现UserDetails接口，这样我们的SysUser就可以直接拿到spring security中使用了
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "系统用户")
@TableName(value = "user")  //由于service层使用到了mybatis-plus，且数据库的表名与这里的类名不一致，所以必须指定表名
public class SysUser extends BasePO {

    @TableId(value = "user_id",type = IdType.AUTO) //user表的主键字段与这里的属性名不一致，所以需要明确指定
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    private String password;

    private String nickname;

    private String realName;

    private String sex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String remark;

    private Boolean enabled;

    public SysUser(){}

    public SysUser(String username) {
        this.username = username;
    }

    //仅供LoginUser使用
    public boolean getEnabled() {

        if (enabled == null){
            return true;
        }

        return this.enabled;
    }
}
