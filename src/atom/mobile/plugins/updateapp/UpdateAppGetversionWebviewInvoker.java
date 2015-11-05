package atom.mobile.plugins.updateapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/27.
 */
public class UpdateAppGetversionWebviewInvoker implements IWebViewInvoker {
    @Override
    public void invoke(Map data, BaseWebViewActivity act, EventHandler eventHandler) throws Exception {
        Map res=new HashMap();
        res.put("verCode",getCurrentVerCode(act));
        res.put("verName",getCurrentVerName(act));
        eventHandler.fireEvent("getVersion",res);
    }
    private String getCurrentVerName(Activity act) {
        String packageName = act.getPackageName();
        String currentVerName = "";
        try {
            currentVerName = act.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return currentVerName;
    }
    private int getCurrentVerCode(Activity act) {
        String packageName = act.getPackageName();
        int currentVer = -1;
        try {
            currentVer = act.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return currentVer;
    }
}
