

#import "AtomBaseViewController.h"

@interface AtomBaseViewController ()
{
    
}
@end

@implementation AtomBaseViewController




- (void)loadPage:(NSString*)urlString webView: (UIWebView*)webView
{
   [AtomUtil loadPage:urlString webView:webView];
}




@end
