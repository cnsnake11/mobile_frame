package atom.mobile.frame.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import atom.mobile.frame.R;
import atom.mobile.frame.util.WebViewUtil;

/**
 * Created by cn on 2015/4/29.
 */
public class OpenWebViewActivity extends BaseWebViewActivity implements View.OnClickListener {

    protected WebView wv;

    @Override
    public WebView getWebView() {
        return wv;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.frame_openwebview);


        String url=getIntent().getStringExtra("url");
        url="file:///android_asset/"+url;
        String title=getIntent().getStringExtra("title");
        String dataFromParent=getIntent().getStringExtra("dataFromParent");
        this.dataFromParent=dataFromParent;

        TextView textView=(TextView)this.findViewById(R.id.title);
        textView.setText(title);

        TextView menuImg = (TextView) findViewById(R.id.titleBack);
        menuImg.setOnClickListener(this);

        wv= WebViewUtil.initWebView(this, R.id.webview, true);
        wv.loadUrl(url);
//        wv.addJavascriptInterface(this, "$native");

    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.titleBack){
            OpenWebViewActivity.this.finish();
        }

    }






}