package atom.mobile.frame.viewpaper.widgets;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import atom.mobile.frame.viewpaper.webview.AtomWebViewPagerAdapter;
import atom.mobile.frame.viewpaper.webview.AtomWebViewPaper;

import java.util.List;

/**
 * Created by cn on 2015/4/28.
 */
public class WebViewPaperWithTab {

    //对外
    Activity act;
    int viewPaperId;
    AtomWebViewPaper[] papers;
        //tab
    int[] tabIds;
    int[] tabSrc1s;
    int[] tabSrc2s;


    //对内
    ViewPager viewPager;

    AtomWebViewPagerAdapter apa;


    public WebView getCurrentWebView(){
        //return  papers[viewPager.getCurrentItem()].getWebView();
        return (WebView) apa.getViewList().get(viewPager.getCurrentItem());
    }



    public WebViewPaperWithTab(Activity act, int viewPaperId, AtomWebViewPaper[] papers, int[] tabIds, int[] tabSrc1s, int[] tabSrc2s) {
        this.viewPaperId = viewPaperId;
        this.act = act;
        this.papers = papers;
        this.tabIds = tabIds;
        this.tabSrc1s = tabSrc1s;
        this.tabSrc2s = tabSrc2s;
    }

    public  void init(){

        viewPager=(ViewPager)act.findViewById(viewPaperId);
        viewPager.setOnPageChangeListener(myPageChangeListener);
        apa=new AtomWebViewPagerAdapter(act,papers);
        viewPager.setAdapter(apa);
        viewPager.setOffscreenPageLimit(this.papers.length);
        initTab();
    }



    private ViewPager.OnPageChangeListener myPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }
        @Override
        public void onPageSelected(int i) {
            changeTab(tabIds[i]);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };




    //tab ---start




    private  int getTabIndex(int tabId){
        int j=0;
        for(int i=0;i<tabIds.length;i++){
            if(tabId==tabIds[i]){
                j=i;
                break;
            }
        }
        return j;
    }

    private void changeTab(int tabId){
        int j=0;
        for(int i=0;i<tabIds.length;i++){
            ImageView image = (ImageView)act.findViewById(tabIds[i]);
            image.setImageResource(tabSrc1s[i]);

            if(tabId==tabIds[i]){
                j=i;
            }
        }
        ImageView image = (ImageView)act.findViewById(tabId);
        image.setImageResource(tabSrc2s[j]);
    }

    private void initTab(){
        for(int i=0;i<tabIds.length;i++){
            ImageView image = (ImageView)act.findViewById(tabIds[i]);
            image.setOnClickListener(tabClickListener);
        }
    }

    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!v.isSelected()) {
                changeTab(v.getId());
                viewPager.setCurrentItem(getTabIndex(v.getId()));
            }
        }
    };

//tab --end



}
