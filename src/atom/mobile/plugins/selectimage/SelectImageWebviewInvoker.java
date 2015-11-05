package atom.mobile.plugins.selectimage;

import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.provider.MediaStore;

import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.activity.RequestCode;
import atom.mobile.frame.util.WebInvokerEventUtil;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;
import atom.mobile.frame.webview.invoker.IWebViewInvokerResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/3.
 */
public class SelectImageWebviewInvoker implements IWebViewInvoker,IWebViewInvokerResult {


    private EventHandler eventHandler;

    @Override
    public void invoke(Map data, BaseWebViewActivity act,EventHandler eventHandler) throws Exception {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        act.setWebViewInvokerResult(this);
        //此处必须用OnWebviewInvokerResult否则不会触发回调onActivityResult
        act.startActivityForResult(i, RequestCode.OnWebviewInvokerResult);

        this.eventHandler=eventHandler;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data,BaseWebViewActivity act) {
        if (resultCode != act.RESULT_OK) {
            return;
        }

        if(data==null){
            return;
        }

        Uri uri = data.getData();
        Cursor cursor = act.getContentResolver().query(uri, null, null, null,null);
        if (cursor != null && cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            //此处不进行旋转了，会造成太多冗余文件，目前三星手机图片方向不准
//            String imgPath = DirUtil.requestFilePath("selectImage", "jpg");
//            ImageUtil.rotate(path,imgPath);

            Map dataMap=new HashMap();
            dataMap.put("path",path);
            eventHandler.fireEvent("selectImageFinish",dataMap);
        }

    }



}
