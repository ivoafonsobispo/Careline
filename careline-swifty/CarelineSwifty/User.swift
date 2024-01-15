//
//  UserMeasures.swift
//  CarelineSwifty
//
//  Created by formando on 30/11/2023.
//

import Foundation
import Combine

class User: ObservableObject {
    
    @Published var nus: String
    @Published var name: String
    @Published var email: String
    var measures: [Measure]
    @Published var token: String 
    
    init() {
        self.nus = ""
        self.name = ""
        self.email = ""
        self.measures = [
            Heartbeat(measured: false, value: -99),
            Temperature(measured: false, value: -99.99)
        ]
        self.token = ""
    }
}
