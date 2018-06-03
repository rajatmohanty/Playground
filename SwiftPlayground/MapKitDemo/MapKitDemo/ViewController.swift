//
//  ViewController.swift
//  MapKitDemo
//
//  Created by 宋 奎熹 on 2018/2/13.
//  Copyright © 2018年 宋 奎熹. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate {
    
    @IBOutlet weak var addressTV: UITextView!
    @IBOutlet weak var latitudeTF: UITextField!
    @IBOutlet weak var longitudeTF: UITextField!
    
    lazy var geoc: CLGeocoder = {
        return CLGeocoder()//CLGeocoder地理编码要使用的类
    }()
    let locationManager = CLLocationManager()

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
        locationManager.desiredAccuracy = kCLLocationAccuracyThreeKilometers
        locationManager.stopUpdatingLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let changeLocation: [CLLocation] = locations
        let currentLocation: CLLocation = changeLocation.last!
        latitudeTF.text = "\(currentLocation.coordinate.latitude)"
        longitudeTF.text = "\(currentLocation.coordinate.longitude)"
    }
    
    @IBAction func geocClick() {
        let address = addressTV.text!
        if address.count == 0 {
            return
        }
        geoc.geocodeAddressString(address, completionHandler: {(clpls : [CLPlacemark]?, error : Error? ) in
            //地标对象可以拿到的信息: location : 用户的位置(经纬度信息)// name : 地址// locality : 城市// country : 国家
            if error != nil {
                return
            }
            
            // 1.获取地标对象(第一个相关度是最高的)
            guard let clpl = clpls?.first else {
                return
            }
            // 2.设置地址
            self.addressTV.text = "country:\(clpl.country ?? "")\ncity:\(clpl.locality ?? "")\ndistrict:\(clpl.subLocality ?? "")"
            // 3.设置latitudeTF该显示的经纬度
            self.latitudeTF.text = clpl.location?.coordinate.latitude.description ?? ""
            self.longitudeTF.text = clpl.location?.coordinate.longitude.description ?? ""
        })
    }
    
    //监听反地理编码方法的点击
    @IBAction func reverseGeocClick() {
        let latitude = CLLocationDegrees(latitudeTF.text!) ?? 0
        let longitude = CLLocationDegrees(longitudeTF.text!) ?? 0
        let location = CLLocation(latitude: latitude, longitude: longitude)
        /// 反地理编码的方法
        geoc.reverseGeocodeLocation(location, completionHandler: {(clpls : [CLPlacemark]?,error : Error? )in
            if error != nil {
                print(error?.localizedDescription)
                return
            }
            // 1.获取地标对象(第一个相关度是最高的)
            guard let clpl = clpls?.first else {
                return
            }
            // 2.设置地址
            self.addressTV.text = "Country:\(clpl.country ?? "")\nCity:\(clpl.locality ?? "")\nDistrict:\(clpl.subLocality ?? "")"
            // 3.设置经纬度
            self.latitudeTF.text = clpl.location?.coordinate.latitude.description ?? ""
            self.longitudeTF.text = clpl.location?.coordinate.longitude.description ?? ""
        })
    }
    
}

