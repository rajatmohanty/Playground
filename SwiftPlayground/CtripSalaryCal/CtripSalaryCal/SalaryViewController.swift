//
//  SalaryViewController.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class SalaryViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet var tableView: UITableView!
    @IBOutlet var totalAmountLabel: UILabel!
    
    var salaries = [SalaryModel]()
    let dateFMT = DateFormatter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
        
        self.dateFMT.dateFormat = "yyyy-MM-dd"
        self.totalAmountLabel.text = "0"
        
        self.totalAmountLabel.font = UIFont.init(name: "PingFangSC-Light", size: 45.0)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        salaries = SalaryIOHelper.sharedInstance.readSalary() ?? [SalaryModel]()
        salaries.sort(by: {$0.date > $1.date})
        self.totalAmountLabel.text = "\(SalaryCalculator.sharedInstance.calculateSumSalaries(salaries: self.salaries))"
        tableView.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table View
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return salaries.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = self.tableView?.cellForRow(at: indexPath) as? SalaryTableViewCell
        
        if(cell == nil){
            let nibList = Bundle.main.loadNibNamed("SalaryTableViewCell", owner: nil, options: nil)
            cell = nibList?[0] as? SalaryTableViewCell
        }
        
        let salary = self.salaries[indexPath.row]
        cell?.amountLabel.text = "\(salary.amount!) 元"
        cell?.orderNumberLabel.text = "\(salary.orderNum!) 单"
        cell?.dateLabel.text = "\(self.dateFMT.string(from: salary.date))"
        
        return cell!
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
