package atom.mobile.plugins.takephoto;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.activity.RequestCode;
import atom.mobile.frame.util.DirUtil;
import atom.mobile.frame.util.ImageUtil;
import atom.mobile.frame.util.WebInvokerEventUtil;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;
import atom.mobile.frame.webview.invoker.IWebViewInvokerResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/3.
 */
public class TakePhotoWebviewInvoker implements IWebViewInvoker,IWebViewInvokerResult {


    EventHandler eventHandler ;
    @Override
    public void invoke(Map data, BaseWebViewActivity act,EventHandler eventHandler) throws Exception {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imgPath = DirUtil.requestFilePath("takePhoto", "jpg");


        /***
         * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
         */
        /*ContentValues values = new ContentValues();
        Uri uri = act.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);*/

        Uri uri = Uri.fromFile(new File(imgPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

        act.setWebViewInvokerResult(this);
        act.startActivityForResult(intent, RequestCode.OnWebviewInvokerResult);//此处必须用OnWebviewInvokerResult否则不会触发回调onActivityResult

        this.eventHandler=eventHandler;
    }

    private String imgPath;// 该照片的绝对路径

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data,BaseWebViewActivity act) {

        if (resultCode != act.RESULT_OK) {
            return;
        }

        //将照片旋转成正常
        ImageUtil.rotate(imgPath,imgPath);

        Map dataMap=new HashMap();
        dataMap.put("path",imgPath);
        eventHandler.fireEvent("takePhotoFinish", dataMap);

    }


}
