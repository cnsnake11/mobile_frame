package atom.mobile.frame.util;

import android.app.Activity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * Created by cn on 2015/5/15.
 */
public class CookieUtil {

    private static String url;
    private static String cookie;

   public  static void initCookie(String serverUrl,Activity act){
       url=serverUrl+"/";

       CookieSyncManager.createInstance(act);
       CookieManager cookieManager = CookieManager.getInstance();
       cookieManager.setAcceptCookie(true);
       String s=cookieManager.getCookie(url);

       cookie=s+";domain="+url;
   }

    public  static void  setWebViewCookie(Activity act){
        if(url!=null&&cookie!=null){
            CookieSyncManager.createInstance(act);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        }

    }




}
