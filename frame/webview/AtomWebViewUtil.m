 


#import "AtomWebViewUtil.h"

@interface AtomWebViewUtil ()
{
 
}

@end






@implementation AtomWebViewUtil

+(WebViewJavascriptBridge*)init:(UIWebView *)webView  uiViewController:(AtomBaseViewController*) uiViewController

{

    WebViewJavascriptBridge* bridge=[WebViewJavascriptBridge bridgeForWebView:webView handler:^(id data, WVJBResponseCallback responseCallback) {
    NSLog(@"Received message from javascript: %@", data);
    responseCallback(@"Right back atcha");
}];






//响应JS通过callhandler发送给OC的消息
    [bridge registerHandler:@"open" handler:^(id data, WVJBResponseCallback responseCallback) {
    
       NSLog(@"open called: %@", data);
     
       
      //responseCallback(@"Response from testObjcCallback");
      
      
    //获取storyboard: 通过bundle根据storyboard的名字来获取我们的storyboard,
    UIStoryboard *story = [UIStoryboard storyboardWithName:@"atomCommWebView" bundle:[AtomUtil getBundle]];
    
    //由storyboard根据myView的storyBoardID来获取我们要切换的视图
    AtomCommWebViewController *wv = [story instantiateViewControllerWithIdentifier:@"atomCommWebView"];
    
    wv.parent=uiViewController;
    
    //传值
    wv.caption=[data objectForKey:@"caption"];
    wv.pathForResource=[data objectForKey:@"pathForResource"];
    wv.ofType=[data objectForKey:@"ofType"];
    wv.inDirectory=[data objectForKey:@"inDirectory"];
    wv.params=[data objectForKey:@"params"];
    
    
    UIBarButtonItem *backItem=[[UIBarButtonItem alloc]init];
    backItem.title=@"返回";
    uiViewController.navigationItem.backBarButtonItem=backItem;
    
    //由navigationController推向我们要推向的view
    [uiViewController.navigationController pushViewController:wv animated:YES];
      
      
      
    }];

    
    
    
    [bridge registerHandler:@"invoke" handler:^(id data, WVJBResponseCallback responseCallback) {
     
        NSLog(@"invoke called: %@", data);
        
        NSString *clz =[data valueForKey:@"clz"];
        id userData=[data valueForKey:@"data"];
        id eventNames=[data valueForKey:@"eventNames"];
        
        Class cls = NSClassFromString(clz);
        
        EventHandler* eventH=[[EventHandler alloc] init];
        eventH.viewControler=uiViewController;
        eventH.eventNames=eventNames;
        
        //启动invoker之前先置空之前的，注意如果是多线程启动了多个invoker可能会有问题
        //考虑到原则上invoker在同一时刻只允许一个能运行，所以目前可以这样写
        //todo 嵌套调用invoker的时候，可能会有问题，后期重构
        //目前用到这个功能的有【selectImage，takePhoto】
        uiViewController.curInvoker=nil;
        
        id invoker= [[cls alloc] init];
        [invoker invoke:userData uiViewController:uiViewController eventHandler:eventH];
    
        
    }];
    
    
     [bridge registerHandler:@"close" handler:^(id data, WVJBResponseCallback responseCallback) {
     
        NSLog(@"close called: %@", data);
        NSString *dataStr =[data valueForKey:@"dataStr"];
        
        
        if([uiViewController isKindOfClass:[AtomBaseViewController class]]){
            AtomBaseViewController *a=uiViewController;
            [[uiViewController.parent getBridge] callHandler:@"fireOpenClose" data:dataStr];
        }
        
        [uiViewController.navigationController popViewControllerAnimated:YES];
        
    }];
    
    
    
    [bridge registerHandler:@"_setServerUrl" handler:^(id data, WVJBResponseCallback responseCallback) {
     
        NSLog(@"_setServerUrl called: %@", data);
        NSString *url =[data valueForKey:@"url"];
        [SettingUtil setServerUrl:url];
        
    }];
    
    
    if([uiViewController isKindOfClass:[AtomCommWebViewController class]]){
        AtomCommWebViewController *a=uiViewController;
        [bridge callHandler:@"setUpParams" data:a.params];
    }else{
        [bridge callHandler:@"setUpParams" data:nil];
    }


    return bridge;

}

 


@end




@interface UIWebView (JavaScriptAlert)

- (void)webView:(UIWebView *)sender runJavaScriptAlertPanelWithMessage:(NSString *)message initiatedByFrame:(id *)frame;
- (BOOL)webView:(UIWebView *)sender runJavaScriptConfirmPanelWithMessage:(NSString *)message initiatedByFrame:(id *)frame;

@end

@implementation UIWebView (JavaScriptAlert)
 static BOOL status = NO;
 static BOOL isEnd =NO;

- (void)webView:(UIWebView *)sender runJavaScriptAlertPanelWithMessage:(NSString *)message initiatedByFrame:(id *)frame {
    
    UIAlertView* customAlert = [[UIAlertView alloc] initWithTitle:nil
                                                          message:message
                                                         delegate:nil
                                                cancelButtonTitle:@"确定"
                                                otherButtonTitles:nil];
    
    [customAlert show];
    
}





- (BOOL)webView:(UIWebView *)sender runJavaScriptConfirmPanelWithMessage:(NSString *)message initiatedByFrame:(id)frame {
   UIAlertView *confirmDiag = [[UIAlertView alloc] initWithTitle:nil
                                                         message:message
                                                        delegate:self
                                               cancelButtonTitle:@"取消"
                                               otherButtonTitles:@"确定",nil];
    
    [confirmDiag show];
    
    CGFloat version = [[[UIDevice currentDevice] systemVersion]floatValue];
   if (version >= 7.) {
       while (isEnd ==NO) {
            [[NSRunLoop mainRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.01f]];

        }
    }else
    {
       while (isEnd ==NO && confirmDiag.superview !=nil) {
            [[NSRunLoop mainRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.01f]];
        }
    }
   isEnd = NO;
   return status;
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
   status = buttonIndex;
   isEnd = YES;
}
@end
