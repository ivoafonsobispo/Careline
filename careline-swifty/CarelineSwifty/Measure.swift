//
//  Measure.swift
//  CarelineSwifty
//
//  Created by formando on 16/11/2023.
//

import Foundation

struct Measure: Codable, Identifiable {
    var id = UUID()
    
    var symbol: String
    var name: String
    var data: String
    
    static var sampleMeasures: Measure {
        return Measure(symbol: "heart", name: "Heartbeat", data: "70")
        
    }
    
}
