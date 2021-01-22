package hiram.image.pojo.query;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 16:31
 * @Description: ""
 */

@Data
public class InfraredDescriptionAddViewQuery {

    private Long infraredId;
    private String description;
    private String severityLevel;
    private Long descriptionXCoordinate;
    private Long descriptionYCoordinate;

}
