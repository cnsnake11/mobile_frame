//
//  AtomBaseViewController+selectimage.m
//  hpApp
//
//  Created by Terry on 15/7/29.
//
//

#import "AtomBaseViewController+selectimage.h"

@implementation AtomBaseViewController (selectimage)


//当选择一张图片后进入这里
-(void)imagePickerController:(UIImagePickerController*)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
 
{
 
 
   NSLog(@"imagePickerController called ");
  
    NSString *type = [info objectForKey:UIImagePickerControllerMediaType];
 
    NSString* filePath;
    //当选择的类型是图片
    if ([type isEqualToString:@"public.image"])
    {
    
        
    
        //先把图片转成NSData
        UIImage* image = [info objectForKey:@"UIImagePickerControllerOriginalImage"];
        
        
        UIImageOrientation imageOrientation=image.imageOrientation;
        if(imageOrientation!=UIImageOrientationUp)
        {
            // 原始图片可以根据照相时的角度来显示，但UIImage无法判定，于是出现获取的图片会向左转９０度的现象。
            // 以下为调整图片角度的部分
            UIGraphicsBeginImageContext(image.size);
            [image drawInRect:CGRectMake(0, 0, image.size.width, image.size.height)];
            image = UIGraphicsGetImageFromCurrentImageContext();
            UIGraphicsEndImageContext();
            // 调整图片角度完毕
        }
        
        image=[self thumbnailWithImageWithoutScale:image size:CGSizeMake(image.size.width*0.2, image.size.height*0.2)];
        
        NSData *data=UIImagePNGRepresentation(image);
        if (data == nil)
        {
            data = UIImageJPEGRepresentation(image, 0.8);
        }
 
        //图片保存的路径
        //这里将图片放在沙盒的documents文件夹中
        NSString * DocumentsPath = [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"];  
 
        double random= [NSDate date].timeIntervalSince1970 ;
        NSString* fileName=[[NSString alloc]initWithFormat:@"/%f%@",random,  @"_selectimageTmp.png"];

       
 
        //文件管理器
        NSFileManager *fileManager = [NSFileManager defaultManager];
 
        //把刚刚图片转换的data对象拷贝至沙盒中 并保存为image.png
        [fileManager createDirectoryAtPath:DocumentsPath withIntermediateDirectories:YES attributes:nil error:nil];

        [fileManager createFileAtPath:[DocumentsPath stringByAppendingString:fileName] contents:data attributes:nil];
 
        //得到选择后沙盒中图片的完整路径
        filePath = [[NSString alloc]initWithFormat:@"%@%@",DocumentsPath,  fileName];
 
        //关闭相册界面
        [picker dismissModalViewControllerAnimated:YES];
 
        
        [self.curInvoker selectFinish:filePath];
        self.curInvoker=nil;//如果invoker设置了这个curInvoker，则不用的时候必须要置空
        
    }
    
    
    
}





//2.保持原来的长宽比，生成一个缩略图
- (UIImage *)thumbnailWithImageWithoutScale:(UIImage *)image size:(CGSize)asize
{
  UIImage *newimage;
  if (nil == image) {
    newimage = nil;
  }
  else{
    CGSize oldsize = image.size;
    CGRect rect;
    if (asize.width/asize.height > oldsize.width/oldsize.height) {
      rect.size.width = asize.height*oldsize.width/oldsize.height;
      rect.size.height = asize.height;
      rect.origin.x = (asize.width - rect.size.width)/2;
      rect.origin.y = 0;
    }
    else{
      rect.size.width = asize.width;
      rect.size.height = asize.width*oldsize.height/oldsize.width;
      rect.origin.x = 0;
      rect.origin.y = (asize.height - rect.size.height)/2;
    }
    UIGraphicsBeginImageContext(asize);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context, [[UIColor clearColor] CGColor]);
    UIRectFill(CGRectMake(0, 0, asize.width, asize.height));//clear background
    [image drawInRect:rect];
    newimage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
  }
  return newimage;
}

@end



