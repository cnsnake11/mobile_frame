

#import <UIKit/UIKit.h>
#import "WebViewJavascriptBridge.h"
#import "AtomUtil.h"

@interface AtomBaseViewController : UIViewController

  - (void)loadPage:(NSString*)urlString webView: (UIWebView*)webView;


  /*
    此方法子类必须实现， 否则运行时会出现错误
    获得当前控制器中的js桥对象
  */
  -(WebViewJavascriptBridge*) getBridge;


/*
   打开这个控制器的父亲控制器，
   要求在打开一个新的AtomBaseViewController的时候，由父亲控制器来为儿子控制器赋此值
*/
  @property AtomBaseViewController* parent;


/*
    当前的invoker对象.由invoker赋值，由后续处理方法置空【比如选择图片的回调方法】
*/
  @property id curInvoker;

@end
