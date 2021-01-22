package hiram.image.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/5/20 20:22
 * @Description: ""
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("img_infrared")
public class Infrared extends BasePO {

    @TableId(value = "infrared_id",type = IdType.AUTO)
    private Long infraredId;

    private String perspective;
    private String path;
    private String filename;
    private String url;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime scanTime;

    private Long patientId;
    private Long userId;
}
