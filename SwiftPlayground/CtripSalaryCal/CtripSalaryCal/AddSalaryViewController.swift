//
//  AddSalaryViewController.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class AddSalaryViewController: UIViewController {
    
    @IBOutlet weak var monthText: UITextField!
    @IBOutlet weak var dayText: UITextField!
    @IBOutlet weak var orderNumberText: UITextField!
    @IBOutlet weak var amountLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.backgroundColor = UIColor.init(red: 245.0/255.0, green: 245.0/255.0, blue: 245.0/255.0, alpha: 1.0)
        
        orderNumberText.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        
        let date = Date()
        let dateformatter = DateFormatter()
        dateformatter.dateFormat = "MM-dd"
        let dateString = dateformatter.string(from: date)
        
        let index1 = dateString.index(dateString.startIndex, offsetBy: 2)
        let index2 = dateString.index(dateString.startIndex, offsetBy: 3)
        monthText.text = dateString.substring(to: index1)
        dayText.text = dateString.substring(from: index2)
        
        monthText.font = UIFont.init(name: "PingFangSC-Light", size: 25.0)
        dayText.font = UIFont.init(name: "PingFangSC-Light", size: 25.0)
        orderNumberText.font = UIFont.init(name: "PingFangSC-Light", size: 25.0)
        amountLabel.font = UIFont.init(name: "PingFangSC-Light", size: 45.0)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @objc func textFieldDidChange(_ sender: UITextField) {
        amountLabel.text = String(SalaryCalculator.sharedInstance.calculateSalaryOfOrderNumber(number: Int(orderNumberText.text!) ?? 0))
    }
    
    @IBAction func didFinishAddSalary() {
        let inputMonth = Int(monthText.text!) ?? -1
        let inputDay = Int(dayText.text!) ?? -1
        let inputOrderNum = Int(orderNumberText.text!) ?? -1
        
        print(inputMonth, inputDay, inputOrderNum)
        
        if inputMonth <= 0 || inputMonth >= 13 || inputDay <= 0 || inputDay >= 32 || inputOrderNum < 0 {
            let alertC = UIAlertController.init(title: "输入数据有误", message: "请检查输入月份、日期和订单数是否输入正确", preferredStyle: .alert)
            alertC.addAction(UIAlertAction.init(title: "好的", style: .default, handler: nil))
            self.present(alertC, animated: true, completion: nil)
            return
        }
        
        let dateFormat = DateFormatter()
        dateFormat.dateFormat = "yyyy"
        let year = dateFormat.string(from: Date())
        dateFormat.dateFormat = "MM"
        let month = dateFormat.string(from: Date())
        dateFormat.dateFormat = "dd"
        let day = dateFormat.string(from: Date())
        dateFormat.dateFormat = "yyyy-MM-dd"
        let newDate = dateFormat.date(from: "\(year)-\(monthText.text ?? month)-\(dayText.text ?? day)")
        
        SalaryIOHelper.sharedInstance.writeSalary(salary: SalaryModel(date: newDate!, orderNum: inputOrderNum, amount: Int(amountLabel.text!)!))
        self.navigationController?.popViewController(animated: true)
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
