package atom.mobile.frame.webview.invoker;

import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.util.WebInvokerEventUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/22.
 */
public class EventHandler {

    private Map<String,String> eventNames=new HashMap<String, String>();
    private BaseWebViewActivity act;

    public EventHandler(Map<String, String> eventNames, BaseWebViewActivity act) {
        this.eventNames = eventNames;
        this.act = act;
    }

    public void fireEvent(String name,Map data){

        String realName=eventNames.get(name);
        if(realName!=null&&!"".equals(realName)){
            WebInvokerEventUtil.fireEvent(realName,data,act.getWebView());
        }else{
            System.out.println("页面没有注册事件:"+name+",将不触发fireEvent");
        }



    };

}
