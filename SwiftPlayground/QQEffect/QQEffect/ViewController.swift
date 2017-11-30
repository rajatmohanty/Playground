//
//  ViewController.swift
//  QQEffect
//
//  Created by 宋 奎熹 on 2017/9/26.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    let leftMove = CABasicAnimation(keyPath: "position")
    let rightMove = CABasicAnimation(keyPath: "position")
    let doorScale = CABasicAnimation(keyPath: "transform.scale")
    let boomEffect = CABasicAnimation(keyPath: "position")

    override func viewDidLoad() {
        super.viewDidLoad()
        
        leftMove.fromValue = NSValue(cgPoint: CGPoint(x: self.view.center.x, y: self.view.center.y))
        leftMove.toValue = NSValue(cgPoint: CGPoint(x: self.view.center.x - 10, y: self.view.center.y))
        leftMove.duration = 0.2
        leftMove.repeatCount = 2
        leftMove.autoreverses = true
        
        rightMove.fromValue = NSValue(cgPoint: CGPoint(x: self.view.center.x, y: self.view.center.y))
        rightMove.toValue = NSValue(cgPoint: CGPoint(x: self.view.center.x + 20, y: self.view.center.y))
        rightMove.duration = 0.2
        rightMove.repeatCount = 2
        rightMove.autoreverses = true
        
        doorScale.fromValue = 1
        doorScale.toValue = 0.95
        doorScale.duration = 0.2
        doorScale.repeatCount = 2
        doorScale.autoreverses = true
        
        boomEffect.fromValue = NSValue(cgPoint: CGPoint(x: self.view.center.x - CGFloat(arc4random() % 10), y: self.view.center.y))
        boomEffect.toValue = NSValue(cgPoint: CGPoint(x: self.view.center.x + CGFloat(arc4random() % 10), y: self.view.center.y))
        boomEffect.duration = 0.05
        boomEffect.repeatCount = 5
        boomEffect.autoreverses = true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    @IBAction func addAnimation(_ sender: UIButton) {
        if sender.tag == 0 {
            rootViewController()?.view.layer.add(leftMove, forKey: "leftMove")
        } else if sender.tag == 1 {
            rootViewController()?.view.layer.add(rightMove, forKey: "rightMove")
        } else if sender.tag == 2 {
            rootViewController()?.view.layer.add(doorScale, forKey: "doorScale")
        } else if sender.tag == 3 {
            rootViewController()?.view.layer.add(boomEffect, forKey: "boom")
        }
    }
    
    private func rootViewController() -> UIViewController? {
        return UIApplication.shared.keyWindow?.rootViewController
    }

}

