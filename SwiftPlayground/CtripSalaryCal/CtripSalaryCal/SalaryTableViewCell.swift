//
//  SalaryTableViewCell.swift
//  CtripSalaryCal
//
//  Created by 宋 奎熹 on 2017/7/19.
//  Copyright © 2017年 宋 奎熹. All rights reserved.
//

import UIKit

class SalaryTableViewCell: UITableViewCell {
    
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var orderNumberLabel: UILabel!
    @IBOutlet weak var amountLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.dateLabel.font = UIFont.init(name: "PingFangSC-Light", size: 15.0)
        self.orderNumberLabel.font = UIFont.init(name: "PingFangSC-Light", size: 15.0)
        self.amountLabel.font = UIFont.init(name: "PingFangSC-Light", size: 20.0)
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
