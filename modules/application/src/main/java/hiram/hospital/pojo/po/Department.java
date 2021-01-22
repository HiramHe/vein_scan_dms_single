package hiram.hospital.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 15:57
 * @Description: ""
 */

@Data
@TableName("hos_department")
public class Department extends BasePO {

    @TableId(value = "department_id",type = IdType.AUTO)
    private Long departmentId;
    private String departmentName;
    private String remark;

}
