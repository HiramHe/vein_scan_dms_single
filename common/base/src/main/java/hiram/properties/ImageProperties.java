package hiram.properties;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 16:45
 * @Description: ""
 */

@Component
@ConfigurationProperties(prefix = "image")
@Data
public class ImageProperties implements InitializingBean {

    private String infraredDirectoryOnWin = "G:/test/infrared";
    private String ultrasoundDirectoryOnWin = "G:/test/ultrasound";

    private String infraredDirectoryOnLinux = "/tmp/infrared";
    private String ultrasoundDirectoryOnLinux = "/tmp/ultrasound";

    private String infraredDirectory = "";
    private String ultrasoundDirectory = "";

    private Boolean enableAliyunOss;

    //重构路径，根据操作系统类型，设置图像存储路径
    private void setDirectory(){

        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
        boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");

        //保证路径以 “/” 为结束符
        infraredDirectoryOnWin = infraredDirectoryOnWin.endsWith("/") ?
                infraredDirectoryOnWin : infraredDirectoryOnWin + "/";

        ultrasoundDirectoryOnWin = ultrasoundDirectoryOnWin.endsWith("/") ?
                ultrasoundDirectoryOnWin : ultrasoundDirectoryOnWin + "/";

        infraredDirectoryOnLinux = infraredDirectoryOnLinux.endsWith("/") ?
                infraredDirectoryOnLinux : infraredDirectoryOnLinux + "/";

        ultrasoundDirectoryOnLinux = ultrasoundDirectoryOnLinux.endsWith("/") ?
                ultrasoundDirectoryOnLinux : ultrasoundDirectoryOnLinux + "/";

        //设置图像存储路径
        if (isWin){
            infraredDirectory = infraredDirectoryOnWin;
            ultrasoundDirectory = ultrasoundDirectoryOnWin;
        }else if (isLinux){
            infraredDirectory = infraredDirectoryOnLinux;
            ultrasoundDirectory = ultrasoundDirectoryOnLinux;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setDirectory();
    }
}
