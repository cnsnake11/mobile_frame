
#import <UIKit/UIKit.h>
#import "AtomBaseViewController.h"

@interface EventHandler:NSObject


@property NSDictionary* eventNames;
@property AtomBaseViewController* viewControler;


-(void) fireEvent:(NSString*) name data:(NSDictionary*)data;

@end