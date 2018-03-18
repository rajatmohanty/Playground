//
//  ViewController.m
//  GCDDemo
//
//  Created by 宋 奎熹 on 2018/3/17.
//  Copyright © 2018年 宋 奎熹. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    NSLog(@"Testing sync");

    dispatch_sync(dispatch_get_main_queue(), ^{
        NSLog(@"Deadlock");
    });
    
    NSLog(@"Testing async");

    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"No Deadlock");
    });
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
