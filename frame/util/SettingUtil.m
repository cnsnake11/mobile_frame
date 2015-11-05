
#import "SettingUtil.h"

 
static NSString *serverUrl;

@implementation SettingUtil


 + (NSString*)getServerUrl {
    if(serverUrl==nil||[serverUrl isEqual:@""]){
        [NSException raise:@"serverUrl is null." format:@""];
    }
    return serverUrl;
 }

 + (void)setServerUrl: (NSString*) url {
    serverUrl=url;
 }


@end
