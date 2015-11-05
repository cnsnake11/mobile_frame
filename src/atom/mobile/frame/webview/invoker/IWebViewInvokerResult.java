package atom.mobile.frame.webview.invoker;

import android.content.Intent;
import atom.mobile.frame.activity.BaseWebViewActivity;

/**
 * Created by cn on 2015/7/9.
 */
public interface IWebViewInvokerResult {
    public void onActivityResult(int requestCode, int resultCode, Intent data,BaseWebViewActivity act) ;
}
