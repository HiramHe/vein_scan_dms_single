package hiram.image.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/10/4 18:49
 * @Description: ""
 */

@Data
@TableName("img_description_bultrasound")
public class DescriptionBultrasound extends BasePO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long descriptionId;
    private Long bultrasoundId;

    private Long bultrasoundXCoordinate;
    private Long bultrasoundYCoordinate;
}
