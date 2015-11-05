package atom.mobile.plugins.upload;


import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.activity.OpenWebViewActivity;
import atom.mobile.frame.util.JsonUtil;
import atom.mobile.frame.util.UploadUtil;
import atom.mobile.frame.util.WebInvokerEventUtil;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cn on 2015/7/3.
 */
public class UploadWebviewInvoker implements IWebViewInvoker {

    @Override
    public void invoke(final  Map data,final BaseWebViewActivity act, final  EventHandler eventHandler) throws Exception {

        new Thread( new Runnable() {//单独启动线程，webview不会死机
            public void run() {
                OpenWebViewActivity openWebViewActivity;
                if(act instanceof OpenWebViewActivity){
                    openWebViewActivity= (OpenWebViewActivity) act;
                }else {
                    throw new RuntimeException("类【"+act.getClass()+"】不支持使用invoke方法。");
                }

                String filePath= (String) data.get("filePath");
                File file=new File(filePath);
                String res= UploadUtil.uploadFile(file);

                String fileId=getFileId(res);
                Map dataMap=new HashMap();
                dataMap.put("fileId",fileId);
                eventHandler.fireEvent("uploadFinish", dataMap);
            }
        }).start();

    }


    public static String getFileId(String s) {
        if(s==null||"".equals(s)){
            return "";
        }

        s=s.replaceAll("<div id=\"AtomPageData\"  style='display:none' data='","");
        s=s.replaceAll("'></div>","");
        Map root=JsonUtil.getJsonObject(s);

        List list= (List) root.get("data");
        if(list.size()==0){
            return "";
        }

        Map map= (Map) list.get(0);
        String fileId= (String) map.get("value");

        return fileId;
    }


}
