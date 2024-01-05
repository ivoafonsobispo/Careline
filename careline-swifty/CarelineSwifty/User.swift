//
//  UserMeasures.swift
//  CarelineSwifty
//
//  Created by formando on 30/11/2023.
//

import Foundation
import Combine

class User: ObservableObject {
    
    @Published var name: String
    var measures: [Measure]
    @Published var token: String 
    
    init() {
        self.name = "José"
        self.measures = [
            Heartbeat(measured: false, value: -99),
            Temperature(measured: false, value: -99.99)
        ]
        self.token = ""
    }
}
