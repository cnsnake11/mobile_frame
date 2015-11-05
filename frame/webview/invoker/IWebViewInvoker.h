

#import <UIKit/UIKit.h>
#import "AtomBaseViewController.h"
#import "EventHandler.h"

@protocol IWebViewInvoker

@required
  -(void)invoke:(NSDictionary*)data  uiViewController:(AtomBaseViewController*) uiViewController eventHandler:(EventHandler*) eventHandler;

@end