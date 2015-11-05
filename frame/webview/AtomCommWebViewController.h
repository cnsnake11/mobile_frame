



#import <UIKit/UIKit.h>
#import "AtomBaseViewController.h"
#import "WebViewJavascriptBridge.h"
#import "AtomWebViewUtil.h"

@interface AtomCommWebViewController : AtomBaseViewController



@property NSString *pathForResource;
@property NSString *inDirectory;
@property NSString *ofType;
@property NSString *caption;
@property NSString *params;
 
@end