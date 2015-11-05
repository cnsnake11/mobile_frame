package atom.mobile.frame.viewpaper.webview;

/**
 * Created by cn on 2015/4/28.
 */
public class AtomWebViewPaper {



        private String url;
        boolean isLoaded=false;
        int wvId;

        public AtomWebViewPaper(String url, int wvId) {
            this.url = url;
            this.wvId = wvId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public void setIsLoaded(boolean isLoaded) {
            this.isLoaded = isLoaded;
        }

        public int getWvId() {
            return wvId;
        }

        public void setWvId(int wvId) {
            this.wvId = wvId;
        }
 }
