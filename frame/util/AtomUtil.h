



#import <UIKit/UIKit.h>


@interface AtomUtil:NSObject


+ (void)loadPage:(NSString*)urlString webView: (UIWebView*)webView;


+ (void)setRootViewController:(UIViewController *)rootViewController curViewController:(UIViewController *)curViewController;

+(NSBundle*) getBundle;

 
@end