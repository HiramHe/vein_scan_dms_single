package hiram.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: HiramHe
 * @Date: 2020/9/25 20:23
 * @Description: ""
 */

public class FileUtils {

    public static Set<String> imageExtensions = new HashSet<>(
            Arrays.asList("jpg","png","jpeg")
    );

    public static boolean isImage(String originFileName){

        if(originFileName == null){
            return false;
        }

        String extension = originFileName.contains(".") ?
                originFileName.substring(originFileName.lastIndexOf(".")+1) : null;

        if(extension == null){
            return false;
        }

        return imageExtensions.contains(extension);
    }
}
