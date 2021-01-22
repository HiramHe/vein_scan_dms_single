package hiram.image.pojo.vo;

import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.po.InfraredDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/8/5 16:34
 * @Description: ""
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class InfraredVO extends Infrared {

    private String patientName;

    private String username;

    private List<InfraredDescription> infraredDescriptions;
}
