package hiram.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author: HiramHe
 * @Date: 2020/4/23 20:32
 * @Description: "公共实体类"
 */

@Data
public class BasePO implements Serializable {

    //接口的参数是对象时，让本字段不显示在swagger中
    @ApiModelProperty(hidden = true)
    @TableLogic
    private Boolean deleted = false;

    @ApiModelProperty(value = "创建时间:yyyy-MM-dd HH:mm:ss",hidden = true)
    //自动填充数据库生成的时间
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间:yyyy-MM-dd HH:mm:ss",hidden = true)
    @TableField(value = "gmt_modify",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime gmtModify;

    @ApiModelProperty(hidden = true)
    @Version
    private Long version = 1L;
}
