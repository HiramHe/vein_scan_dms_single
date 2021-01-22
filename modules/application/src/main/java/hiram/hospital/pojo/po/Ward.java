package hiram.hospital.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 16:02
 * @Description: ""
 */

@Data
@TableName("hos_ward")
public class Ward extends BasePO {

    @TableId("ward_id")
    private Long wardId;
    private Long wardNumber;
    private String wardName;

}
