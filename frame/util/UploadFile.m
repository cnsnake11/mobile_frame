
#import "UploadFile.h"  
  
@implementation UploadFile  

  
 - (NSString*)uploadFile: (NSString*) filePath
{

    NSString* serverUrl=[SettingUtil getServerUrl];

    NSString *urlString = @"/upload.atom?ctrl=FileHandleCtrl_save&atomFileMaxSize=-1&atomUploadMaxSize=-1&postData={\"ctrl\":\"FileHandleCtrl_save\"}";

    urlString=[NSString stringWithFormat:@"%@%@",serverUrl,urlString];

    urlString = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
      
    NSData *data = [NSData dataWithContentsOfFile:filePath];
    
    NSURL *url=[NSURL URLWithString:urlString];
    
   

//创建Request对象  
NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];  
    [request setURL:url];
    [request setHTTPMethod:@"POST"];  
    NSMutableData *body = [NSMutableData data];  
  
//设置表单项分隔符  
    NSString *boundary = @"---------------------------14737809831466499882746641449";  
  
//设置内容类型  
    NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@",boundary];  
    [request addValue:contentType forHTTPHeaderField: @"Content-Type"];  
  
    //写入图片的内容  
    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];  
    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"; filename=\"PIC_DATA1.jpg\"\r\n",@"PIC_DATA1"] dataUsingEncoding:NSUTF8StringEncoding]];  
    [body appendData:[@"Content-Type: image/jpeg\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];  
    //[body appendData:[infoDic objectForKey:@"PIC_DATA1"]];
    [body appendData:data];
    [body appendData:[[NSString stringWithFormat:@"\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];  
      
    //写入INFO的内容  
    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];  
    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n",@"PIC_INFO"] dataUsingEncoding:NSUTF8StringEncoding]];  
    //[body appendData:jsonData];
    [body appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];  
  
     //写入尾部内容  
    [body appendData:[[NSString stringWithFormat:@"--%@--\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];  
  
    [request setHTTPBody:body];  
      
    NSHTTPURLResponse *urlResponese = nil;
    NSError *error = [[NSError alloc]init];
    NSData* resultData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponese error:&error];
    
//    NSDictionary *responseDic = [NSJSONSerialization JSONObjectWithData:resultData options:NSJSONReadingMutableLeaves error:nil];
    
    NSString *result = [[NSString alloc] initWithData:resultData encoding:NSUTF8StringEncoding];
    NSLog(@"upload result = %@", result);
    
    return result;
}
  
  
  
@end  