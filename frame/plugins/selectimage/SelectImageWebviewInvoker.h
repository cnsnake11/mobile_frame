

#import <UIKit/UIKit.h>
#import "IWebViewInvoker.h"


@interface SelectImageWebviewInvoker:NSObject<IWebViewInvoker>


-(void)selectFinish:(NSString*) filePath;


 @property AtomBaseViewController* uiViewController;
 @property EventHandler* eventHandler;
 
@end
