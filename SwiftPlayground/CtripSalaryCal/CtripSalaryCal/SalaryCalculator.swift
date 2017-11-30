//
//  SalaryCalculator.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class SalaryCalculator: NSObject {
    
    static let sharedInstance = SalaryCalculator()
    
    override private init() {}
    
    func calculateSalaryOfOrderNumber(number: Int) -> Int {
        var count = number * 15
        if number >= 10 {
            count += 90
        } else if number >= 6 {
            count += 60
        }
        return count
    }
    
    func calculateSumSalaries(salaries: [SalaryModel]) -> Int {
        var total = 0
        for salary in salaries {
            total += salary.amount
        }
        return total
    }
    
}
