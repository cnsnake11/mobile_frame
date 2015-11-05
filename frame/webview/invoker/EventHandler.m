//
//  EventHandler.m
//  hpApp
//
//  Created by Terry on 15/7/29.
//
//

 #import "EventHandler.h"


 @implementation EventHandler
 

-(void) fireEvent:(NSString *)name data:(NSDictionary *)data{

//    String realName=eventNames.get(name);
//        if(realName!=null&&!"".equals(realName)){
//            WebInvokerEventUtil.fireEvent(realName,data,act.getWebView());
//        }else{
//            System.out.println("页面没有注册事件:"+name+",将不触发fireEvent");
//        }[data valueForKey:@"clz"];

    NSString* realName=[self.eventNames valueForKey:name];
    
    if (realName!=nil) {
    
        NSMutableDictionary* eventData=[[NSMutableDictionary alloc] init];
        [eventData setValue:realName forKey:@"realName"];
        [eventData setValue:data forKey:@"data"];
    
        [[self.viewControler getBridge] callHandler:@"_fireEvent" data:eventData];
    }else{
        NSLog(@"页面没有注册事件:%@,将不触发fireEvent",name);
    }

}

 @end