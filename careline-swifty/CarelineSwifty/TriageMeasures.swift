//
//  TriageMeasures.swift
//  CarelineSwifty
//
//  Created by formando on 27/11/2023.
//

import Foundation

struct TriageMeasures: Codable, Identifiable {
    var id = UUID()
    
    var heartbeat: Measure
    var heartbeatMeasured: Bool = false
    var bodyTemperature: Measure
    var bodyTemperatureMeasured: Bool = false
    var symptons: String
    
}
