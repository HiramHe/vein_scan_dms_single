package hiram.springSecurity.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2021/1/16 11:27
 * @Description: ""
 */

@Data
public class SecurityUser implements Serializable {

    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String realName;

    private String sex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String email;

    private String phoneNumber;

    private String avatar;

    private String remark;

    private Boolean enabled;
}
