package hiram.image.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Author: HiramHe
 * @Date: 2020/8/5 16:55
 * @Description: ""
 */

@Data
@TableName("img_infrared_description")
public class InfraredDescription extends BasePO {

    @TableId(value = "description_id",type = IdType.AUTO)
    private Long descriptionId;

    private Long infraredId;
    private String description;
    private String severityLevel;
    private Long descriptionXCoordinate;
    private Long descriptionYCoordinate;

}
