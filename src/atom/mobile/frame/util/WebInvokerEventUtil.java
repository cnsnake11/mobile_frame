package atom.mobile.frame.util;

import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/11.
 */
public class WebInvokerEventUtil {

    public static void fireEvent(String name,Map data,WebView webView){
        if(data==null){
            data=new HashMap();
        }
        String dataStr=JsonUtil.mapToJson(data);
        webView.loadUrl("javascript:$m.webInvoker._fireEvent('"+name+"','"+dataStr+"')");

    }

}
