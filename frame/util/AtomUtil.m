#import "AtomUtil.h"



@implementation AtomUtil


+ (void)loadPage:(NSString*)urlString webView: (UIWebView*)webView{

    NSURL *url =[NSURL URLWithString:urlString];
    
    NSURLRequest *request =[NSURLRequest requestWithURL:url];
    
    [webView loadRequest:request];

}



+ (void)setRootViewController:(UIViewController *)rootViewController curViewController:(UIViewController *)curViewController{


 typedef void (^Animation)(void);
 UIWindow* window = curViewController.view.window;
  
// rootViewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
 Animation animation = ^{
  BOOL oldState = [UIView areAnimationsEnabled];
  [UIView setAnimationsEnabled:NO];
//  window.rootViewController = rootViewController;
  [window setRootViewController:rootViewController];
  [UIView setAnimationsEnabled:oldState];
 };
  
 [UIView transitionWithView:window
       duration:0.6f
        options:UIViewAnimationOptionTransitionFlipFromTop
     animations:animation
     completion:nil];
    
}


+(NSBundle*) getBundle{
    NSString * bundlePath = [[ NSBundle mainBundle] pathForResource: @ "Frameworks/mobile_frame" ofType :@ "framework"];
    NSBundle* bundle = [NSBundle bundleWithPath:bundlePath];
    return bundle;
    
//    return [NSBundle mainBundle];
}

@end