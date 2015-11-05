package atom.mobile.frame.webview.invoker;

import atom.mobile.frame.activity.BaseWebViewActivity;

import java.util.Map;


/**
 * webview 调用执行器接口
 */
public interface IWebViewInvoker {


    /**
     *
     * @param data webview传入的数据，json格式自动转成的map
     * @param act 当前webview所处的activity对象
     * @param eventHandler 事件处理器，可以用来触发js注册的事件
     */
    public void invoke(Map data,BaseWebViewActivity act,
                       EventHandler eventHandler) throws Exception;


}
