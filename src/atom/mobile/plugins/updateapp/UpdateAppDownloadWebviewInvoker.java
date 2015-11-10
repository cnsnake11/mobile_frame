package atom.mobile.plugins.updateapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;
import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;
/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by cn on 2015/7/27.
 */
public class UpdateAppDownloadWebviewInvoker implements IWebViewInvoker {
    @Override
    public void invoke(Map data, BaseWebViewActivity act, EventHandler eventHandler) throws Exception {
        this.downloadPath = (String) data.get("apkPath");
        this.act=act;
        showDownloadDialog();
    }

    private Activity act;
    private String downloadPath;
    String UPDATE_SERVERAPK = "updateApkTmp.apk";

    private ProgressDialog pd = null;
    private boolean isCanceled=false;

    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                //dismissDialog();
                pd.cancel();
                isCanceled=true;
            }
            return false;
        }
    };
/*
    public void dismissDialog() {
        if (isFinishing()) {
            return;
        }
        if (null != pd && pd.isShowing()) {
            pd.dismiss();
        }
    }

    *//**
     * cancel progress dialog if nesseary
     *//*
    @Override
    public void onBackPressed() {
        if (pd != null && pd.isShowing()) {
            dismissDialog();
        } else {
            super.onBackPressed();
        }
    }*/

    private void showDownloadDialog() {
        pd = new ProgressDialog(act);

        pd.setOnKeyListener(onKeyListener);
        pd.setCancelable(false);

        pd.setTitle("正在下载");
        pd.setMessage("请稍候...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        downloadApk();
    }
    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        //new downloadApkThread().start();
        downFile(downloadPath);
    }

    /**
     * 下载apk
     */
    private Thread thread;
    public void downFile(final String url) {/*
        pd.show();
        thread= new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    fileSize = entity.getContentLength();
                    InputStream is = entity.getContent();
                    FileOutputStream fileOutputStream = null;

                    if (is != null) {
                        boolean mExternalStorageAvailable = false;
                        boolean mExternalStorageWriteable = false;
                        String state = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(state)) {//已经插入了sd卡，并且可以读写
                            File file = new File(Environment.getExternalStorageDirectory(), UPDATE_SERVERAPK);
                            fileOutputStream = new FileOutputStream(file);
                            byte[] b = new byte[1024];
                            int charb = -1;

                            while ((charb = is.read(b)) != -1) {
                                fileOutputStream.write(b, 0, charb);
                                downLoadFileSize += charb;
                                sendMsg(1);

                                if(isCanceled){
                                    return;
                                }
                            }
                        }
                        fileOutputStream.flush();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
//                        down();
                        sendMsg(2);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        thread.start();*/
    }


    private void sendMsg(int flag)
    {
        Message msg = new Message();
        msg.what = flag;
        handler.sendMessage(msg);
    }

    long fileSize  =0;
    int downLoadFileSize =0;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted())
            {
                switch (msg.what)
                {
                    case 0:
                        //pd.setMax(fileSize);
                    case 1:
                        pd.setProgress(downLoadFileSize);
                        long result = downLoadFileSize * 100 / fileSize;
                        //tv.setText(result + "%");
                        pd.setMessage(result + "%");
                        break;
                    case 2:
                        //Toast.makeText(act, "文件下载完成", Toast.LENGTH_SHORT).show();
                        pd.cancel();
                        update();
                        break;

                    case -1:
                        String error = msg.getData().getString("error");
                        Toast.makeText(act, error, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

 /*   Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pd.cancel();
            update();
        }
    };*/

    /**
     * 下载完成，通过handler将下载对话框取消
     */
   /* public void down() {
        new Thread() {
            public void run() {
                Message message = handler.obtainMessage();
                handler.sendMessage(message);
            }
        }.start();
    }*/

    /**
     * 安装应用
     */
    public void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UPDATE_SERVERAPK))
                , "application/vnd.android.package-archive");
        act.startActivity(intent);
    }
}
