package hiram.oss.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HiramHe
 * @Date: 2021/1/5 17:29
 * @Description: ""
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssResult {

    private String path;
    private String filename;
    private String url;
}
