




#import <UIKit/UIKit.h>
#import "WebViewJavascriptBridge.h"
#import "AtomBaseViewController.h"
#import "AtomCommWebViewController.h"
#import "IWebViewInvoker.h"
#import "AtomUtil.h"
#import "EventHandler.h"
#import "SettingUtil.h"


@interface AtomWebViewUtil:NSObject




 + (WebViewJavascriptBridge*)init:(UIWebView*)webView uiViewController:(UIViewController*) uiViewController;

 
@end

