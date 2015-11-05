//
//  AtomBaseViewController+selectimage.h
//  hpApp
//
//  Created by Terry on 15/7/29.
//
//

#import "AtomBaseViewController.h"
#import "SelectImageWebviewInvoker.h"

@interface AtomBaseViewController (selectimage)

-(void)imagePickerController:(UIImagePickerController*)picker didFinishPickingMediaWithInfo:(NSDictionary *)info;

@end
