package hiram.image.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: HiramHe
 * @Date: 2020/8/5 17:05
 * @Description: ""
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("img_bultrasound")
public class Bultrasound extends BasePO {

    @TableId(value = "bultrasound_id",type = IdType.AUTO)
    private Long bultrasoundId;

    private String path;
    private String filename;
    private String url;
}
