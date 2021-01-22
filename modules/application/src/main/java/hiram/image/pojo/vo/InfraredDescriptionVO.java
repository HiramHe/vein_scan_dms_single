package hiram.image.pojo.vo;

import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.InfraredDescription;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/8/5 17:58
 * @Description: ""
 */

@Data
public class InfraredDescriptionVO extends InfraredDescription {

    private Bultrasound bultrasound;
}
