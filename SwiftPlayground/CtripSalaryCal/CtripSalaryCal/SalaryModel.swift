//
//  SalaryModel.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class SalaryModel: NSObject, NSCoding {
    
    var date: Date!
    var orderNum: Int!
    var amount: Int!
    
    init(date: Date, orderNum: Int, amount: Int) {
        self.date = date
        self.orderNum = orderNum
        self.amount = amount
    }
    
    required init(coder aDecoder:NSCoder){
        self.date = aDecoder.decodeObject(forKey: "date") as! Date
        self.orderNum = aDecoder.decodeObject(forKey: "orderNum") as! Int
        self.amount = aDecoder.decodeObject(forKey: "amount") as! Int
    }
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(date, forKey: "date")
        aCoder.encode(orderNum, forKey: "orderNum")
        aCoder.encode(amount, forKey: "amount")
    }
}
