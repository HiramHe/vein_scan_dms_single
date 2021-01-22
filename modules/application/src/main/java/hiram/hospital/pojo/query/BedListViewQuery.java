package hiram.hospital.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 18:49
 * @Description: ""
 */

@Data
public class BedListViewQuery {

    private Long bedNumber;

    @ApiParam(defaultValue = "true")
    private Boolean vacant;
}
