




#import "AtomCommWebViewController.h"

@interface AtomCommWebViewController ()
{
IBOutlet UIWebView *commWebView;

}
@property WebViewJavascriptBridge* bridge;
@end



@implementation AtomCommWebViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    
        
    NSString *filePath = [[NSBundle mainBundle] pathForResource:self.pathForResource ofType:self.ofType inDirectory:self.inDirectory];
    
    [super loadPage:filePath webView:commWebView];
    
    
    //必须要加在自己上，否则不触发delegate事件
    self.bridge = [AtomWebViewUtil init:commWebView uiViewController:self];
    
    //[self.bridge callHandler:@"setUpParams" data:self.params];
    
    
}

-(void) viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBarHidden = NO;
    [self setTitle:self.caption];
}


- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    if (![[self.navigationController viewControllers] containsObject:self])
    {
        //NSLog(@"用户点击了返回按钮");
         [[self.parent getBridge] callHandler:@"fireOpenClose" data:nil];
    }
}


  -(WebViewJavascriptBridge*) getBridge{
    return  self.bridge;
  }


@end

