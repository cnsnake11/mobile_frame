package atom.mobile.frame.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.*;


 import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;


/**
 * Created by cn on 2015/4/27.
 */
public class WebViewUtil {

    public static WebView initWebView(Activity act,int webViewId){
       return  new WebViewUtil().initWebView2(act,webViewId,true);
    }

    public static WebView initWebView(Activity act,int webViewId,boolean isProgress){
        return  new WebViewUtil().initWebView2(act,webViewId,isProgress);
    }

    private  WebView initWebView2(Activity act,int webViewId,boolean isProgress){


        WebView webView=(WebView)act.findViewById(webViewId);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.addJavascriptInterface(act, "$native");

        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setDatabasePath("/data/data/" + webView.getContext().getPackageName() + "/databases/");
        }


        webView.setWebViewClient(new WebViewClientImp(act,isProgress) {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                /*if(isProgress==true)progressBar.show();*/
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                /*if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }*/
            }
        });

        webView.setWebChromeClient(new WebChromeClientImp(act) );

        CookieUtil.setWebViewCookie(act);

        return webView;
    }




    private class WebViewClientImp extends WebViewClient{
        ProgressDialog progressBar ;
        boolean isProgress=true;
        Activity act;

        public WebViewClientImp(Activity act,boolean isProgress) {
            this.act=act;
            this.progressBar = new ProgressDialog(act);
            this.isProgress=isProgress;
        }



    }


    /**
     * 解决webview中无法弹出alert的问题
     */
    private class WebChromeClientImp extends WebChromeClient {

        Activity act;

        public WebChromeClientImp(Activity act) {
            this.act = act;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            AlertDialog.Builder b2 = new AlertDialog.Builder(this.act)
                    /*.setTitle("提示")*/.setMessage(message)
                    .setPositiveButton("确定",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.confirm();
                                }
                            });
            b2.setCancelable(false);
            b2.create();
            b2.show();
            return true;
        }


        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(message)
                    .setPositiveButton("确定", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    }).setNeutralButton("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });

            // 禁止响应按back键的事件　
             builder.setCancelable(false);

            builder .create().show();

            return true;
        }

    }
}
