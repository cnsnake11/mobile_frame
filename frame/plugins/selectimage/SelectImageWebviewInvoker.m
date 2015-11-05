

#import "SelectImageWebviewInvoker.h"



@implementation SelectImageWebviewInvoker


 -(void)invoke:(NSDictionary*)data  uiViewController:(AtomBaseViewController*) uiViewController eventHandler:(EventHandler *)eventHandler{
 
 
    NSLog(@"SelectImageWebviewInvoker called: %@", data);
    
    uiViewController.curInvoker=self;
    
    self.uiViewController=uiViewController;
    self.eventHandler=eventHandler;
    
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    picker.delegate = uiViewController;
    //设置选择后的图片可被编辑
    //picker.allowsEditing = YES;
    [uiViewController presentModalViewController:picker animated:YES];
 
 }


  -(void)selectFinish:(NSString*) filePath{
 
 
        NSMutableDictionary* data=[[NSMutableDictionary alloc] init];
        [data setValue:filePath forKey:@"path"];
 
        [self.eventHandler fireEvent:@"selectImageFinish" data:data];
 
 }


@end

