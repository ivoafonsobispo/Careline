//
//  UserMeasures.swift
//  CarelineSwifty
//
//  Created by formando on 30/11/2023.
//

import Foundation

class User {
    
    //var id: Int
    var name: String
    /**var age: Int
    var sex: String
    var healthNumber: String
    var activated: Bool*/
    var measures: [Measure]
    
    init() {
        self.name = "José"
        self.measures = [
                Heartbeat(measured: false, value: -99),
                Temperature(measured: false, value: -99.99)
            ]
    }
    
}
