package atom.mobile.frame.util;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cn on 2015/7/10.
 */
public class DirUtil {


//创建目录
        public static String makeDir(String moudleName){

            //todo 不同的app如何区分不同的目录。需要引入配置文件等办法
            String strImgPath = Environment.getExternalStorageDirectory()
                    .toString() + "/atom/"+moudleName+"/";


            File out = new File(strImgPath);
            if (!out.exists()) {
                out.mkdirs();
            }

            return strImgPath;
        }


    //申请file path
    public static String requestFilePath(String moudleName, String suffix){

        String fileName = new SimpleDateFormat("yyyyMMdd")
                .format(new Date()) +"-"+UUID.randomUUID() + "."+suffix;
        String dirName=makeDir(moudleName);
        return dirName+fileName;
    }


}
