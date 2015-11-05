

#import "UploadWebviewInvoker.h"
 

@implementation UploadWebviewInvoker


 -(void)invoke:(NSDictionary*)data  uiViewController:(AtomBaseViewController*) uiViewController eventHandler:(EventHandler *)eventHandler{
 
 
    NSLog(@"UploadWebviewInvoker called: %@", data);
     
    self.uiViewController=uiViewController;
    self.eventHandler=eventHandler;
    
    UploadFile *upload = [[UploadFile alloc] init];  
 
    NSString *filePath = [data valueForKey:@"filePath"];
     
    NSString* res= [upload uploadFile:filePath];
    NSString* fileId=[self getFileId:res];
    
    
    NSMutableDictionary* resData=[[NSMutableDictionary alloc] init];
    [resData setValue:fileId forKey:@"fileId"];

    [self.eventHandler fireEvent:@"uploadFinish" data:resData];
 
 }


/*

<div id="AtomPageData"  style='display:none' data='{"data":[{"name":"fileId","value":"1438331804938_847338616439500150","type":""}]}'></div>

*/
 -(NSString*) getFileId:(NSString*) s{
    
    if(s==nil||[s isEqualToString:@""]){
        return @"";
    }
    
    s=[s stringByReplacingOccurrencesOfString:@"<div id=\"AtomPageData\"  style='display:none' data='" withString:@""];
    s=[s stringByReplacingOccurrencesOfString:@"'></div>" withString:@""];
    
    
    NSError *error = nil;
    NSData * data = [s dataUsingEncoding:NSUTF8StringEncoding]; //NSString转换成NSData类型
    
    NSDictionary *root = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:&error];

    NSArray* list=[root valueForKey:@"data"];
    if([list count]==0){
        return @"";
    }
    
    NSDictionary* map=list[0];
    NSString* fileId= [map valueForKey:@"value"];
    
    
    return fileId;
 }





@end

