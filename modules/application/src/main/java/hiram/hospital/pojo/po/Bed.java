package hiram.hospital.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 16:29
 * @Description: ""
 */

@Data
@TableName("hos_bed")
public class Bed extends BasePO {

    @TableId(value = "bed_id", type = IdType.AUTO)
    private Long bedId;
    private Long bedNumber;
    private Boolean vacant;
}
