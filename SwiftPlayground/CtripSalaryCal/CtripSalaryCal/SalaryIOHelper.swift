//
//  SalaryIOHelper.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class SalaryIOHelper: NSObject {
    
    static let sharedInstance = SalaryIOHelper()
    
    override private init() {}
    
    func writeSalary(salary: SalaryModel) {
        var salaries = readSalary() ?? [SalaryModel]()
        salaries.append(salary)
        let data = NSKeyedArchiver.archivedData(withRootObject: salaries)
        UserDefaults.standard.set(data, forKey: "salaries")
        UserDefaults.standard.synchronize()
    }
    
    func readSalary() -> (Array<SalaryModel>?) {
        if let data = UserDefaults.standard.data(forKey: "salaries") {
            return NSKeyedUnarchiver.unarchiveObject(with: data) as? [SalaryModel] ?? [SalaryModel]()
        }
        return [SalaryModel]()
    }
}
