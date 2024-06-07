//
//  TriageMeasures.swift
//  CarelineSwifty
//
//  Created by formando on 27/11/2023.
//

import Foundation

class TriageMeasures {
    var measures: [Measure]
    
    var symptons: String
    
    init(symptons: String, measures: [Measure]) {
        self.symptons = symptons
        self.measures = [
                Heartbeat(measured: false, value: -99),
                Temperature(measured: false, value: -99)
            ]
    }
    
}
