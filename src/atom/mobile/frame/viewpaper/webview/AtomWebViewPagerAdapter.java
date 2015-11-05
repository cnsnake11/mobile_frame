package atom.mobile.frame.viewpaper.webview;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.webkit.WebView;
import atom.mobile.frame.util.WebViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cn on 2015/4/28.
 */
public class AtomWebViewPagerAdapter extends PagerAdapter {

    protected Activity act;

    protected AtomWebViewPaper[] papers;

    private List<View> viewList=new ArrayList<View>();

    public List<View> getViewList() {
        return viewList;
    }

    public AtomWebViewPagerAdapter(Activity act, AtomWebViewPaper[] papers) {
        this.act = act;
        this.papers = papers;
    }

    @Override
    public int getCount() {
        return  papers.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (o);
    }

    @Override
    public Object instantiateItem(View v, int i) {

        if(!papers[i].isLoaded()){
            boolean isP=false;
            if(i==0){
                isP=true;
            }

            WebView webView= WebViewUtil.initWebView(act, papers[i].getWvId(), isP);
            webView.loadUrl(papers[i].getUrl());
            viewList.add(webView);
        }

        return viewList.get(i);
    }

    /*     public void destroyItem(View container,int position,Object object){

           *//* ((ViewGroup) container).removeView((View) object);

            object=null;
*//*
        }*/
}
