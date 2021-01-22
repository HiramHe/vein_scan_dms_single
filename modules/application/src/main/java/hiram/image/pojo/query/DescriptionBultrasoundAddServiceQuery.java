package hiram.image.pojo.query;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2021/1/6 11:19
 * @Description: ""
 */

@Data
public class DescriptionBultrasoundAddServiceQuery {

    private Long descriptionId;
    private Long bultrasoundId;
    private Long bultrasoundXCoordinate;
    private Long bultrasoundYCoordinate;

}
