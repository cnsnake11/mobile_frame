package atom.mobile.frame.util;

import android.webkit.WebView;

/**
 * Created by cn on 2015/7/10.
 */
public class SettingUtil {

    private static String serverUrl;

    public static String getServerUrl() {

        if(serverUrl==null||"".equals(serverUrl)){
            throw new RuntimeException("serverUrl未被初始化。");
        }

        return serverUrl;
    }

    public static void setServerUrl(String serverUrl) {
        SettingUtil.serverUrl = serverUrl;
    }


}
