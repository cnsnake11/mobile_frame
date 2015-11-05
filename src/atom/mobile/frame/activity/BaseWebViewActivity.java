package atom.mobile.frame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import atom.mobile.frame.util.JsonUtil;
import atom.mobile.frame.util.SettingUtil;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;
import atom.mobile.frame.webview.invoker.IWebViewInvokerResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;


public abstract class BaseWebViewActivity extends FragmentActivity {


    public abstract WebView getWebView();



    @JavascriptInterface
    public void _open(String url,String title,String data){
        Intent intent=new Intent(this,OpenWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("dataFromParent", data);

//        this.startActivity(intent);
        this.startActivityForResult(intent,RequestCode.OpenWebViewActivity);
    }

    @JavascriptInterface
    public String _getDataFromParent(){
        return this.dataFromParent;
    }
    protected String  dataFromParent;





    @JavascriptInterface
    public void _invoke(String json){
        try {
            Map jsonMap= JsonUtil.getJsonObject(json);
            String clz= (String) jsonMap.get("clz");
            Map data= (Map) jsonMap.get("data");
            Map eventNames= (Map) jsonMap.get("eventNames");

            Class aClass = Class.forName(clz);
            IWebViewInvoker invoker= (IWebViewInvoker) aClass.newInstance();

            EventHandler eventHandler=new EventHandler(eventNames,this);

            invoker.invoke(data,(OpenWebViewActivity)this,eventHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JavascriptInterface
    public void _close(String dataStr){
        Intent intent=new Intent();
        intent.putExtra("dataStr", dataStr);
        this.setResult(RequestCode.OpenWebViewActivity,intent);//todo 这里用RequestCode.OpenWebViewActivity没起到任何作用
        this.finish();
    }


    @JavascriptInterface
    public void _setServerUrl(String serverUrl){
        SettingUtil.setServerUrl(serverUrl);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == RequestCode.OpenWebViewActivity)
        {
//            if(this instanceof OpenWebViewActivity){
             if(intent!=null){
                String dataStr=intent.getStringExtra("dataStr");
                this.getWebView().loadUrl("javascript:$m.android._openClosed(" + dataStr+")");
            }else {
                this.getWebView().loadUrl("javascript:$m.android._openClosed()");
            }

//            }
        }else if(requestCode==RequestCode.OnWebviewInvokerResult){
            if(webViewInvokerResult!=null){
                IWebViewInvokerResult webViewInvokerResultCopy=webViewInvokerResult;
                webViewInvokerResult=null;
                webViewInvokerResultCopy.onActivityResult(requestCode,resultCode,intent,this);
            }
        }
    }

    public void setWebViewInvokerResult(IWebViewInvokerResult webViewInvokerResult) {
        this.webViewInvokerResult = webViewInvokerResult;
    }

    private  IWebViewInvokerResult webViewInvokerResult;


}
