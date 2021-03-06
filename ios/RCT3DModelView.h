#import "RCT3DModelIO.h"
#import <React/RCTView.h>
#import <SceneKit/SceneKit.h>
#import <React/RCTConvert.h>

@interface RCT3DModelView : UIView

@property (nonatomic) bool isLoading;
@property (nonatomic, copy) NSString* src;
@property (nonatomic) int* type;
@property (nonatomic) float scale;
@property (nonatomic, copy) UIColor* color;
@property (nonatomic, copy) SCNNode* modelNode;

@property (nonatomic, copy) RCTBubblingEventBlock loadModelSuccess;
@property (nonatomic, copy) RCTBubblingEventBlock loadModelError;

- (void)loadModel;
- (void)reload;
- (void)addModelNode:(SCNNode *)node;
- (void)removeNode:(SCNNode *)node;
@end
