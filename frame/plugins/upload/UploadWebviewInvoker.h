

#import <UIKit/UIKit.h>
#import "IWebViewInvoker.h"
#import "UploadFile.h"


@interface UploadWebviewInvoker:NSObject<IWebViewInvoker>


 @property AtomBaseViewController* uiViewController;
 @property EventHandler* eventHandler;
 
@end
