

#import "TakePhotoWebviewInvoker.h"

/*
代理类在selectimage目录中定义
与selectimage一起使用：AtomBaseViewController (selectimage)
*/

@implementation TakePhotoWebviewInvoker


 -(void)invoke:(NSDictionary*)data  uiViewController:(AtomBaseViewController*) uiViewController eventHandler:(EventHandler *)eventHandler{
 
 
    NSLog(@"TakePhotoWebviewInvoker called: %@", data);
    
    uiViewController.curInvoker=self;
    
    self.uiViewController=uiViewController;
    self.eventHandler=eventHandler;
    
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    picker.sourceType = UIImagePickerControllerSourceTypeCamera;
    picker.delegate = uiViewController;
    //设置选择后的图片可被编辑
    //picker.allowsEditing = YES;
    [uiViewController presentModalViewController:picker animated:YES];
 
 }


  -(void)selectFinish:(NSString*) filePath{
 
 
        NSMutableDictionary* data=[[NSMutableDictionary alloc] init];
        [data setValue:filePath forKey:@"path"];
 
        [self.eventHandler fireEvent:@"takePhotoFinish" data:data];
 
 }


@end

