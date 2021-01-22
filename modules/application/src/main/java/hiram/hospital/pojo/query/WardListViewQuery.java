package hiram.hospital.pojo.query;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2021/1/22 15:23
 * @Description: ""
 */

@Data
public class WardListViewQuery {

    private Long wardId;
    private Long wardNumber;
    private String wardName;

}
